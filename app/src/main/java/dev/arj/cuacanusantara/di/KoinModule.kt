package dev.arj.cuacanusantara.di

import dev.arj.cuacanusantara.data.weather.repo.WeatherRepository
import dev.arj.cuacanusantara.data.weather.source.WeatherRemoteDataSource
import dev.arj.cuacanusantara.domain.weather.usecase.WeatherUseCase
import dev.arj.cuacanusantara.network.NetworkBuilder
import dev.arj.cuacanusantara.ui.search.SearchViewModel
import dev.arj.cuacanusantara.ui.weather.WeatherViewModel
import org.koin.dsl.module

val networkModule = module {
    factory { NetworkBuilder.getService() }
}

val dataModule = module {
    single { WeatherRemoteDataSource(get()) }
    single { WeatherRepository(get()) }
}

val domainModule = module {
    single { WeatherUseCase(get()) }
}

val viewModelModule = module {
    single { WeatherViewModel(get()) }
    single { SearchViewModel(get()) }
}