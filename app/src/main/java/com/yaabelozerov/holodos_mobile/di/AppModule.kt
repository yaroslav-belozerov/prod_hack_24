package com.yaabelozerov.holodos_mobile.di

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yaabelozerov.holodos_mobile.C
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
import retrofit2.converter.moshi.MoshiConverterFactory
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
    fun provideItemApi(moshi: Moshi): HolodosService {
        return Retrofit.Builder().baseUrl(C.BASE_URL).addConverterFactory(MoshiConverterFactory.create(moshi)).build().create(HolodosService::class.java)
    }

    private val Context.dataStore by preferencesDataStore("settings")

    @Singleton
    class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {
        private val settingsDataStore = appContext.dataStore

        private val uidKey = longPreferencesKey("uid")
        fun getUid(): Flow<Long> = settingsDataStore.data.map { it[uidKey] ?: -1L }
        suspend fun setUid(uid: Long) =
            settingsDataStore.edit { it[uidKey] = uid }

        private val notifyKey = booleanPreferencesKey("notify")
        fun getNotify(): Flow<Boolean> = settingsDataStore.data.map { it[notifyKey] ?: false }
        suspend fun setNotify(notifyExpiry: Boolean) =
            settingsDataStore.edit { it[notifyKey] = notifyExpiry }
    }
}