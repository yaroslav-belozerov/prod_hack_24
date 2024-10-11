package com.yaabelozerov.holodos_mobile.di

import android.content.Context
import androidx.lifecycle.viewmodel.compose.viewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yaabelozerov.holodos_mobile.C
import com.yaabelozerov.holodos_mobile.data.network.ItemApi
import com.yaabelozerov.holodos_mobile.domain.MainScreenViewModel
import com.yaabelozerov.holodos_mobile.domain.network.service.ItemService
import com.yaabelozerov.holodos_mobile.mock.MockItemApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
    fun provideItemApi(): ItemService {
        return if (C.IS_MOCK) MockItemApi() else ItemApi(Retrofit.Builder().baseUrl(C.BASE_URL).build())
    }
}