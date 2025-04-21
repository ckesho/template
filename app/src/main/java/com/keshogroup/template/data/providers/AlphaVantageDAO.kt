package com.keshogroup.template.data.providers

import androidx.annotation.StringRes
import com.google.gson.JsonElement
import com.keshogroup.template.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AlphaVantageDAO() :
    alphaVantageRequests {
    override suspend fun getIntraDayStockInfo(ticker: String): Flow<JsonElement> {

        return flow {
            //emit loading state

            emit(
                AlphaVantageAPI.calls.getTimeSeriesIntraday(
                    symbol = "PDD"
                )
            )

        }.flowOn(Dispatchers.IO)
    }

}

interface alphaVantageRequests {
    suspend fun getIntraDayStockInfo(ticker: String): Flow<Any>
}