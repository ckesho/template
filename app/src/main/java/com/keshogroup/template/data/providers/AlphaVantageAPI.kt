package com.keshogroup.template.data.providers

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

import retrofit2.http.GET
import retrofit2.http.Query

//    https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=5min&apikey=demo


const val API_KEY: String = "Z2E47EV2T8WGX6CE"
const val BASE_URL: String = "https://www.alphavantage.co/"

val gson: Gson = GsonBuilder()
    .setLenient()
//    .serializeSpecialFloatingPointValues()
//    .serializeNulls()
//    .enableComplexMapKeySerialization()
//    .disableInnerClassSerialization()
    .create()
val alphaVantageAPIRetrofitBuilder = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(OkHttpClient.Builder().build())
    .addConverterFactory(ScalarsConverterFactory.create())
    //THIS EVENTUALLY NEEDS TO BE USED
//    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build().create(AlphaVantageAPICalls::class.java)

@Serializable
/*private*/ data class NetworkResponse<T>(
    val data: T,
)

interface AlphaVantageAPICalls {
    @GET("query")
    suspend fun getTimeSeriesIntraday(
        @Query("function") function: String = "TIME_SERIES_INTRADAY",
        @Query("apikey") apikey: String = API_KEY,
        @Query("interval") interval: String = "5min",
        @Query("symbol") symbol: String,
    ): JsonElement

    @GET("query")
    suspend fun getTimeSeriesIntradayV2(
        @Query("function") function: String = "TIME_SERIES_INTRADAY",
        @Query("apikey") apikey: String = API_KEY,
        @Query("interval") interval: String = "5min",
        @Query("symbol") symbol: String,
    ): Flow<NetworkResponse<JsonElement>>
}


object AlphaVantageAPI {
    val calls: AlphaVantageAPICalls by lazy {
        alphaVantageAPIRetrofitBuilder
    }
}


