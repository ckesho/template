package com.keshogroup.template.data.providers

import com.keshogroup.template.data.utilities.getCustomResponse
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

//    // v2 convert to flow
//    override suspend fun getIntraDayStockInfo(ticker: String): Flow<Response<Ticker5Min>> {
//// todo neeed to add call and response to see if my custom response worked
//        return flow {
//            //emit loading state
//            emit(Response.Loading())
//
//            var tickerV2: Ticker5Min = AlphaVantageAPI.calls.getTimeSeriesIntradayV2(
//                symbol = "PDD"
//            )
//
//            emit(
//                Response.Success(tickerV2)
//            )
//
//        }.flowOn(Dispatchers.IO)
//    }

    // v3 convert to Call
    override fun getIntraDayStockInfo(ticker: String): Flow<Response<Ticker5Min>> {
        return flow {
            //emit loading state
            emit(Response.Loading())

            var tickerV3: Response<Ticker5Min> = AlphaVantageAPI
                .calls
                .getTimeSeriesIntradayV3(symbol = "PDD")
                .execute()
                .getCustomResponse()

            emit(
                (tickerV3)
            )
            //note you can use while true along with a delay to keep this flow going

        }.flowOn(Dispatchers.IO)
    }

}

interface alphaVantageRequests {
    fun getIntraDayStockInfo(ticker: String): Flow<Response<Ticker5Min>>
}