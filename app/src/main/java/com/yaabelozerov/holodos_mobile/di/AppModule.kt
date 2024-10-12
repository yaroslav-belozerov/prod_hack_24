package com.yaabelozerov.holodos_mobile.di

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yaabelozerov.holodos_mobile.C
import com.yaabelozerov.holodos_mobile.data.HolodosApi
import com.yaabelozerov.holodos_mobile.domain.network.HolodosService
import com.yaabelozerov.holodos_mobile.mock.MockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(
        @ApplicationContext app: Context,
    ): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Singleton
    @Provides
    fun provideItemApi(): HolodosService {
        return if (C.IS_MOCK) MockApi() else HolodosApi(Retrofit.Builder().baseUrl(C.BASE_URL).build())
    }

    private val Context.dataStore by preferencesDataStore("settings")

    @Singleton
    class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {
        private val settingsDataStore = appContext.dataStore

        private val tokenKey = stringPreferencesKey("token")
        fun getToken(): Flow<String> = settingsDataStore.data.map { it[tokenKey] ?: "" }
        suspend fun setToken(token: String) =
            settingsDataStore.edit { it[tokenKey] = token }

        private val notifyKey = booleanPreferencesKey("notify")
        fun getNotify(): Flow<Boolean> = settingsDataStore.data.map { it[notifyKey] ?: false }
        suspend fun setNotify(notifyExpiry: Boolean) =
            settingsDataStore.edit { it[notifyKey] = notifyExpiry }
    }
}