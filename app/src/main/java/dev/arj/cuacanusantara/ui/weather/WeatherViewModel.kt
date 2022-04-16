package dev.arj.cuacanusantara.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.arj.cuacanusantara.data.weather.mapper.weather.WeatherUiModel
import dev.arj.cuacanusantara.domain.weather.usecase.WeatherUseCase
import dev.arj.cuacanusantara.network.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WeatherViewModel(private val useCase: WeatherUseCase) : ViewModel() {

    private var _weatherState: MutableStateFlow<ViewState<WeatherUiModel>?> = MutableStateFlow(null)
    val weatherState: StateFlow<ViewState<WeatherUiModel>?>
        get() = _weatherState

    fun fetchCurrentWeather(latitude: String, longitude: String) {
        _weatherState.value = ViewState.Loading()

        viewModelScope.launch {
            useCase.fetchCurrentWeather(latitude, longitude)
                .catch { throwable ->
                    _weatherState.value = ViewState.Error(throwable.message)
                }
                .collect { response ->
                    _weatherState.value = ViewState.Success(response)
                }
        }
    }

}