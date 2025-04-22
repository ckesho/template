package com.keshogroup.template.data.extensions

import android.util.Log
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

fun Response<Any>.getCustomResponse(): com.keshogroup.template.data.providers.Response<Any?> {

    try {
        if (this.isSuccessful) {
            return com.keshogroup.template.data.providers.Response.Success(this.body())
        } else {
            var mess: String =
                if (!this.message().isNullOrBlank()) ", message: ${this.message()}" else ""
            var error: String = "${this.code()}: ${this.errorBody().toString()} $mess"

            Log.e("API ERROR", error)
            return com.keshogroup.template.data.providers.Response.Error(error)
        }
    } catch (e: IOException) {
        Log.e("API ERROR IOException", e.toString())
        return com.keshogroup.template.data.providers.Response.Error(e.toString())
    } catch (e: SocketTimeoutException) {
        Log.e("API ERROR SocketTimeoutException", e.toString())
        return com.keshogroup.template.data.providers.Response.Error(e.toString())
    }
}