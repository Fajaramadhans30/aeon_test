package com.app.aeontest.di.module

import com.google.gson.Gson
import com.app.aeontest.BuildConfig
import com.app.aeontest.network.RetrofitService
import com.app.aeontest.network.remote.Api
import com.app.aeontest.network.remote.ApiService
import com.app.aeontest.network.remote.Repository
import com.app.aeontest.viewModel.ViewModell
import com.test.kecipirtest.network.OkhttpClientFactory
import com.test.kecipirtest.network.remote.DataSource
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named

import org.koin.dsl.module

const val BASE_URL = "base_url"

val serviceModule = module {
    single { return@single OkhttpClientFactory.create() }
    single(named(BASE_URL)) { BuildConfig.BASE_URL}
}

val utilityModule = module {
    single { Gson() }
}

//val preferenceModule = module {
//    single { StoryPreference(get()) }
//}
//
val productModule = module {
    single {
        RetrofitService.createReactiveService(
            ApiService::class.java,
            get(),
            get(named(BASE_URL))
        )
    }
    single { Api(get()) }
    single<Repository> { DataSource(get()) }
    viewModel { ViewModell(get()) }
}