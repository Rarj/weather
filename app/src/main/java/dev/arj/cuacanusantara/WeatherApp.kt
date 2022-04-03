package dev.arj.cuacanusantara

import android.app.Application
import dev.arj.cuacanusantara.di.dataModule
import dev.arj.cuacanusantara.di.domainModule
import dev.arj.cuacanusantara.di.networkModule
import dev.arj.cuacanusantara.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WeatherApp)
            modules(networkModule, dataModule, domainModule, viewModelModule)
        }
    }
}