package com.keshogroup.template.data.providers

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.OptionalConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Objects

//    https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=5min&apikey=demo


class AlphaVantageAPI {
//    @Serializable
//    private data class NetworkResponse<T>(
//        val data: T,
//    )

    interface alphaVantageAPICalls {
        @GET("")
        suspend fun getTimeSeriesIntraday(
            @Query("function") function: String = "TIME_SERIES_INTRADAY",
            @Query("apikey") apikey: String = API_KEY,
            @Query("interval") interval: String = "5min",
            @Query("symbol") symbol: String,
        ): Any

        @GET("")
        suspend fun getTimeSeriesIntradayV2(
            @Query("function") function: String = "TIME_SERIES_INTRADAY",
            @Query("apikey") apikey: String = API_KEY,
            @Query("interval") interval: String = "5min",
            @Query("symbol") symbol: String,
        ): Flow<Any>
    }

    fun getAlphaVantageAPI():
            AlphaVantageAPI.alphaVantageAPICalls {
        return Retrofit.Builder()
            .baseUrl(Companion.BASE_URL)
            .addConverterFactory(OptionalConverterFactory.create())
//            .addConverterFactory(Json.)
//            .client()
            .build().create(alphaVantageAPICalls::class.java)
    }

    lateinit var alphaVantageAPI: Retrofit

    companion object {
        const val API_KEY: String = "Z2E47EV2T8WGX6CE"
        const val BASE_URL: String = "https://www.alphavantage.co/"
    }

}