package com.keshogroup.template.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.keshogroup.template.data.alphavantage.models.Ticker5Min
import com.keshogroup.template.data.alphavantage.AlphaVantageDAO
import com.keshogroup.template.data.alphavantage.Response
import com.keshogroup.template.data.common.CommonDAO
import com.keshogroup.template.data.common.CommonDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

data class LoginState(
    val intialstate: Int = 0,
    val userLoggedIn: Boolean = false,
)

private const val LOGIN_NAME = "LOGIN_NAME"

class LoginViewModel(
    //Use this to save state even through os destroy. Handle for keeping login name intact
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    init {
        // todo
    }

    //create a private state flow that can change values. Good for storing the UI state
    private val _loginStateFlow: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())

    //use the private state flow to create a public state flow to be observed and immutable
    val loginStateFlow: StateFlow<LoginState> = _loginStateFlow.asStateFlow()


    //use a flow to create a public state flow to be observed and immutable. Good for storing the data state
    val tickerStateFlow: StateFlow<Response<Ticker5Min>> = getTickerAsStateFlow()

    val loginName = savedStateHandle.getStateFlow(LOGIN_NAME, "")

    fun saveLoginNameForNextTime() {
        savedStateHandle[LOGIN_NAME] = loginName
    }


    //functions that notify
    fun userLoggedin() {
        _loginStateFlow.update { currentLoginState ->
            //use copy to make an copy only changing the items you pass through as parameters
            currentLoginState.copy(userLoggedIn = true)
            //alternatively
            LoginState(intialstate = 0, userLoggedIn = true)
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

    //Database work
    fun saveTicker() {
        viewModelScope.launch {

            getTickerAsStateFlow().collect {

                when (it) {
                    is Response.Error<*> -> {}//TODO()
                    is Response.Initial<*> -> {}//TODO()
                    is Response.Loading<*> -> {}//TODO()
                    is Response.Success<Ticker5Min> -> {
                        Log.i("Carmen", "saveTicker: success")
                        CommonDB.getDB().commonDAO().insertAllTickers(it.data.toEntity())
                    }

                    null -> {
                        Log.i("Carmen", "saveTicker: wtf")
                    }// TODO()
                }
            }
        }
    }

    fun getTickerAsFlowFromDB(): Flow<Ticker5Min> {
        return CommonDB.getDB().commonDAO().findByTickerAsFlow("PDD")
            .map { it?.toModel() ?: Ticker5Min.empty() }
    }


}