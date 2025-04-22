package com.keshogroup.template.data.utilities

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SafeRequestInterceptor : Interceptor {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return try {
            // Attempt the request
            val response = chain.proceed(request)

            // Even if we get a response, it might be an error (4xx/5xx)
            if (!response.isSuccessful) {
                return createErrorResponse(
                    request,
                    response.code,
                    "${response.message} / HTTP Error ${response.code}"
                )
            }

            response

        } catch (e: Exception) {
            // Handle all possible exceptions
            when (e) {
                is IOException -> {
                    if (e is SocketTimeoutException) {
                        createErrorResponse(
                            request,
                            ANDROID_REQUEST_ERROR,
                            "Request timeout ${request.url}"
                        )
                    } else if (e is UnknownHostException) {
                        createErrorResponse(
                            request,
                            ANDROID_REQUEST_ERROR,
                            "Network unavailable ${request.url}"
                        )
                    } else {
                        createErrorResponse(
                            request,
                            ANDROID_REQUEST_ERROR,
                            "Network error ${request.url}"
                        )
                    }
                }

                is HttpException -> {
                    createErrorResponse(request, ANDROID_REQUEST_ERROR, "API error ${request.url}")
                }

                is IllegalStateException -> {
                    createErrorResponse(
                        request,
                        ANDROID_REQUEST_ERROR,
                        "Data parsing error ${request.url}"
                    )
                }

                else -> {
                    createErrorResponse(
                        request,
                        ANDROID_REQUEST_ERROR,
                        "Unexpected error ${request.url}"
                    )
                }
            }
        }
    }

    private fun createErrorResponse(
        originalRequest: Request,
        code: Int,
        message: String
    ): Response {
        //todo LOG ERROR and ANALYTICS HERE
        return Response.Builder()
            .request(originalRequest)
            .protocol(Protocol.HTTP_1_1)
            .code(code)
            .message(message)
            .body(message.toResponseBody())
            .build()
    }

    private fun String.toResponseBody(): ResponseBody {
        return this.toByteArray().toResponseBody("text/plain".toMediaType())
    }

    companion object {
        val ANDROID_REQUEST_ERROR: Int = 512
    }
}