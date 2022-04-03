package dev.arj.cuacanusantara.network

import dev.arj.cuacanusantara.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkBuilder {

    private fun getRetrofitBuilder() = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getService() = getRetrofitBuilder().create(ApiService::class.java)

}