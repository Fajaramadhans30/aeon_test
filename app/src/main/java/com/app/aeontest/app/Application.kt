package com.app.aeontest.app

import android.app.Application
import com.app.aeontest.di.module.serviceModule
import com.app.aeontest.di.KoinContext
import com.app.aeontest.di.module.listModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
class Application :Application(){
    override fun onCreate() {
        super.onCreate()

        KoinContext.initialize(applicationContext)

        startKoin {
            androidContext(this@Application)
            modules(
                listOf(
                    serviceModule,
                    listModule
                )
            )
        }

    }
}