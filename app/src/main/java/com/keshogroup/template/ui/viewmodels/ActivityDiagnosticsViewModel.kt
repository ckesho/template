package com.keshogroup.template.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keshogroup.template.data.models.Ticker5Min
import com.keshogroup.template.data.providers.AlphaVantageDAO
import com.keshogroup.template.data.providers.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

data class ActivityDiagnosticsState(
    val intialstate: Int = 0,
    val internetConnected: Boolean = false,
);

class ActivityDiagnosticsViewModel : ViewModel() {

    //create a private flow that can change values
    private val _activityStateFlow: MutableStateFlow<ActivityDiagnosticsState> =
        MutableStateFlow(ActivityDiagnosticsState())

    //use the private flow to create a public flow to be observed and immutable
    val activityStateFlow: StateFlow<ActivityDiagnosticsState> = _activityStateFlow.asStateFlow()


    //use a flow to create a public state flow to be observed and immutable
    val tickerStateFlow: StateFlow<Response<Ticker5Min>> = getTickerAsStateFlow()

    //use the init block to start collecting even when there are no collectors
    lateinit var tickerStateFlow2: StateFlow<Response<Ticker5Min>>

    init {
        //In this scenario the call to get ticker happens only when this vm is initiated. Because it is a state flow it holds the value for any collector who wants it. Therefore  we can have multiple Screens that may need the same data that can access it from this viewmodel when hoisted properly without having to hit the endpoint multiple times
        tickerStateFlow2 = getTickerAsStateFlow()
    }

    //functions that notify
    fun internetConnected() {
        _activityStateFlow.update { currentLoginState ->
            //use copy to make an copy only changing the items you pass through as parameters
            currentLoginState.copy(internetConnected = true)
            //alternatively
            ActivityDiagnosticsState(intialstate = 0, internetConnected = true)
        }
    }

    fun getTickerAsFlow(ticker: String): Flow<Response<Ticker5Min>> {
        return AlphaVantageDAO().getIntraDayStockInfo(ticker)
    }

    fun getTickerAsStateFlow(): StateFlow<Response<Ticker5Min>> {
        val tickerStateFlow: StateFlow<Response<Ticker5Min>> = getTickerAsFlow("PDD").stateIn(
            initialValue = Response.Initial<Ticker5Min>(),
            scope = viewModelScope,
            //Stops if activity has been placed in back ground
            started = SharingStarted.WhileSubscribed(5000)
        )
        return tickerStateFlow
    }

    //Uses conflate to grab the latest
    fun getLatestTickerAsStateFlow(): StateFlow<Response<Ticker5Min>> {
        val tickerStateFlow: StateFlow<Response<Ticker5Min>> =
            getTickerAsFlow("PDD").conflate().stateIn(
                initialValue = Response.Initial<Ticker5Min>(),
                scope = viewModelScope,
                //Stops if activity has been placed in back ground
                started = SharingStarted.WhileSubscribed(5000)
            )
        return tickerStateFlow
    }
}