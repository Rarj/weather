package dev.arj.cuacanusantara.network

data class ViewState<T>(
    var loading: Boolean = false,
    var success: T? = null,
    var errorMessage: String? = null
)