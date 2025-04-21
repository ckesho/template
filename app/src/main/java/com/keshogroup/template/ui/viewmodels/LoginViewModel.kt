package com.keshogroup.template.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.keshogroup.template.data.providers.AlphaVantageDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginState(
    val intialstate: Int = 0,
    val userLoggedIn: Boolean = false,
) {
    fun getValue(): Unit {

    }
}

class LoginViewModel : ViewModel() {

    //create a private flow that can change values
    private val _loginStateFlow: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())

    //use the private flow to create a public flow to be observed and immutable
    val loginStateFlow: StateFlow<LoginState> = _loginStateFlow.asStateFlow()

    //create a private flow that can change values
    private val _tickerStateFlow: MutableStateFlow<Any> = MutableStateFlow("")

    //use the private flow to create a public flow to be observed and immutable
    val tickerStateFlow: StateFlow<LoginState> = _loginStateFlow.asStateFlow()
    var tickerFlow: Flow<Any> = _loginStateFlow


    //functions that notify
    fun userLoggedin() {
        _loginStateFlow.update { currentLoginState ->
            //use copy to make an copy only changing the items you pass through as parameters
            currentLoginState.copy(userLoggedIn = true)
            //alternatively
            LoginState(intialstate = 0, userLoggedIn = true)
        }
    }

    suspend fun getTicker(ticker: String): Flow<Any> {
        return AlphaVantageDAO().getIntraDayStockInfo(ticker)
    }

}