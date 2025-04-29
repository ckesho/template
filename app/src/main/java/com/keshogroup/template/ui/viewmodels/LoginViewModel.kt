package com.keshogroup.template.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keshogroup.template.data.alphavantage.models.Ticker5Min
import com.keshogroup.template.data.alphavantage.AlphaVantageDAO
import com.keshogroup.template.data.alphavantage.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

data class LoginState(
    val intialstate: Int = 0,
    val userLoggedIn: Boolean = false,
)

private const val LOGIN_NAME = "LOGIN_NAME"

class LoginViewModel(
    //Use this to save state even through os destroy. Handle for keeping login name intact
    private val savedStateHandle: SavedStateHandle
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


}