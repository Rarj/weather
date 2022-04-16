package dev.arj.cuacanusantara.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.arj.cuacanusantara.data.weather.mapper.weather.WeatherUiModel
import dev.arj.cuacanusantara.domain.weather.usecase.WeatherUseCase
import dev.arj.cuacanusantara.network.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(private val useCase: WeatherUseCase) : ViewModel() {

    private var _weatherState: MutableStateFlow<ViewState<WeatherUiModel>?> = MutableStateFlow(null)
    val weatherState: StateFlow<ViewState<WeatherUiModel>?>
        get() = _weatherState

    suspend fun fetchCurrentWeatherByQuery(text: String) = flow<WeatherUiModel> {
        _weatherState.value = ViewState.Loading()

        viewModelScope.launch {
            useCase.fetchCurrentWeatherByQuery(text)
                .catch { throwable ->
                    _weatherState.value = ViewState.Error(throwable.message)
                }
                .collect { response ->
                    _weatherState.value = ViewState.Success(response)
                }
        }
    }.flowOn(Dispatchers.IO)

}