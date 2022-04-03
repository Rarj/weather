package dev.arj.cuacanusantara.network

sealed class ViewState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Loading<T> : ViewState<T>()
    class Success<T>(response: T?) : ViewState<T>(response)
    class Error<T>(errorMessage: String?) : ViewState<T>(message = errorMessage)

}