package com.keshogroup.template.data.providers

import android.util.Log
import androidx.annotation.StringRes
import com.google.gson.JsonElement
import com.keshogroup.template.R
import com.keshogroup.template.data.models.Ticker5Min
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AlphaVantageDAO() :
    alphaVantageRequests {
    //v1
//    override suspend fun getIntraDayStockInfo(ticker: String): Flow<JsonElement> {
//
//        return flow {
//
//            emit(
//                AlphaVantageAPI.calls.getTimeSeriesIntraday(
//                    symbol = "PDD"
//                )
//            )
//
//        }.flowOn(Dispatchers.IO)
//    }

    // v2 convert to flow
    override suspend fun getIntraDayStockInfo(ticker: String): Flow<Response<Ticker5Min>> {
// todo neeed to add call and response to see if my custom response worked
        return flow {
            //emit loading state
            emit(Response.Loading())

            var tickerV2: Ticker5Min = AlphaVantageAPI.calls.getTimeSeriesIntradayV2(
                symbol = "PDD"
            )

            emit(
                Response.Success(tickerV2)
            )

        }.flowOn(Dispatchers.IO)
    }

}

interface alphaVantageRequests {
    suspend fun getIntraDayStockInfo(ticker: String): Flow<Any>
}