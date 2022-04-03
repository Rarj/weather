package dev.arj.cuacanusantara.network

sealed class ViewState<out T> {
    class Loading<T> : ViewState<T>()
    class Success<out R>(val value: R) : ViewState<R>()
    class Failure(val message: String?) : ViewState<Nothing>()
}