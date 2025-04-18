package com.keshogroup.template.data.providers

import androidx.annotation.StringRes
import com.google.gson.JsonElement
import com.keshogroup.template.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AlphaVantageDAO() :
    alphaVantageRequests {
    override fun getIntraDayStockInfo(ticker: String): Flow<JsonElement> {

        return flow {
            emit(
                AlphaVantageAPI.calls.getTimeSeriesIntraday(
                    symbol = "PDD"
                )
            )
        }
    }

    override suspend fun getIntraDayStockInfoV2(ticker: String): Flow<NetworkResponse<JsonElement>> =
        AlphaVantageAPI.calls.getTimeSeriesIntradayV2(
            symbol = "PDD"
        )
}

interface alphaVantageRequests {
    fun getIntraDayStockInfo(ticker: String): Flow<Any>
    suspend fun getIntraDayStockInfoV2(ticker: String): Flow<Any>
}