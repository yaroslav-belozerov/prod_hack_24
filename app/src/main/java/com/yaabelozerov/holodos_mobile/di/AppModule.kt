package com.yaabelozerov.holodos_mobile.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yaabelozerov.holodos_mobile.C
import com.yaabelozerov.holodos_mobile.data.network.ItemApi
import com.yaabelozerov.holodos_mobile.domain.MainScreenViewModel
import com.yaabelozerov.holodos_mobile.domain.network.service.ItemService
import com.yaabelozerov.holodos_mobile.mock.MockItemApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    factory {
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    factory {
        val moshi by inject<Moshi>()
        Retrofit.Builder().baseUrl(C.BASE_URL).addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    single<ItemService> {
//        val retrofit by inject<Retrofit>()
//        ItemApi(retrofit)

        MockItemApi()
    }

    viewModel { MainScreenViewModel(get()) }
}