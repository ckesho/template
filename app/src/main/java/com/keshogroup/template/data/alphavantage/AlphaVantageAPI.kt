package com.keshogroup.template.data.alphavantage

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.keshogroup.template.data.alphavantage.models.Ticker5Min
import com.keshogroup.template.data.utilities.SafeRequestInterceptor
import kotlinx.serialization.Serializable
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(SafeRequestInterceptor()).build()

val alphaVantageAPIRetrofitBuilder = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(client)
    .addConverterFactory(ScalarsConverterFactory.create())
    //THIS EVENTUALLY NEEDS TO BE USED
//    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build().create(AlphaVantageAPICalls::class.java)

@Serializable
/*private*/ data class NetworkResponse<T>(
    val data: T,
)

@Serializable
sealed class Response<T> {
    class Initial<T> : Response<T>()
    class Loading<T> : Response<T>()
    data class Success<T>(val data: T) : Response<T>()
    data class Error<T>(val message: String) : Response<T>()
}

interface AlphaVantageAPICalls {
    //v1 just get the string or a json element
    @GET("query")
    suspend fun getTimeSeriesIntraday(
        @Query("function") function: String = "TIME_SERIES_INTRADAY",
        @Query("apikey") apikey: String = API_KEY,
        @Query("interval") interval: String = "5min",
        @Query("symbol") symbol: String,
    ): JsonElement

    //V2 use https://app.quicktype.io/ to create the model
    @GET("query")
    suspend fun getTimeSeriesIntradayV2(
        @Query("function") function: String = "TIME_SERIES_INTRADAY",
        @Query("apikey") apikey: String = API_KEY,
        @Query("interval") interval: String = "5min",
        @Query("symbol") symbol: String,
    ): Ticker5Min

    //V3 use Call / Response to implement custom handling
    @GET("query")
    fun getTimeSeriesIntradayV3(
        @Query("function") function: String = "TIME_SERIES_INTRADAY",
        @Query("apikey") apikey: String = API_KEY,
        @Query("interval") interval: String = "5min",
        @Query("symbol") symbol: String,
    ): Call<Ticker5Min>
}


object AlphaVantageAPI {
    val calls: AlphaVantageAPICalls by lazy {
        alphaVantageAPIRetrofitBuilder
    }
}


