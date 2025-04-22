package com.keshogroup.template.data.utilities

import android.util.Log
import retrofit2.Response
import java.lang.Exception


fun <T> Response<T>.getCustomResponse(): com.keshogroup.template.data.providers.Response<T> {

    try {
        if (this.isSuccessful) {
            this.body()?.let {
                return com.keshogroup.template.data.providers.Response.Success(data = it)
            }
                ?: return com.keshogroup.template.data.providers.Response.Error(message = "Response successful but empty body")


        } else {
            var mess: String =
                if (!this.message()
                        .isNullOrBlank()
                ) ", message: ${this.message()}" else errorBody().toString()
            var error: String = "${this.code()}: $mess"

            Log.e("API ERROR", error)
            return com.keshogroup.template.data.providers.Response.Error(message = error)
        }
    } catch (e: Exception) {
        Log.e("UNKNOWN API Exception", e.toString())
        return com.keshogroup.template.data.providers.Response.Error(message = e.toString())
    }
}