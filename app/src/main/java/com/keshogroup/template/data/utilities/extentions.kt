package com.keshogroup.template.data.utilities

import android.util.Log
import retrofit2.Response
import java.lang.Exception


fun <T> Response<T>.getCustomResponse(): com.keshogroup.template.data.alphavantage.Response<T> {

    try {
        Log.i("Carmen", "getCustomResponse: Call Made to endpoint")
        if (this.isSuccessful) {
            this.body()?.let {
                return com.keshogroup.template.data.alphavantage.Response.Success(data = it)
            }
                ?: return com.keshogroup.template.data.alphavantage.Response.Error(message = "Response successful but empty body")


        } else {
            var mess: String =
                if (!this.message()
                        .isNullOrBlank()
                ) ", message: ${this.message()}" else errorBody().toString()
            var error: String = "${this.code()}: $mess"

            Log.e("API ERROR", error)
            return com.keshogroup.template.data.alphavantage.Response.Error(message = error)
        }
    } catch (e: Exception) {
        Log.e("UNKNOWN API Exception", e.toString())
        return com.keshogroup.template.data.alphavantage.Response.Error(message = e.toString())
    }
}