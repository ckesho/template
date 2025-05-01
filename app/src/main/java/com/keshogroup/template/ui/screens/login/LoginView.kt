package com.keshogroup.template.ui.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.keshogroup.template.data.alphavantage.models.Ticker5Min
import com.keshogroup.template.data.alphavantage.Response
import com.keshogroup.template.ui.navigation.MainDestinations
import com.keshogroup.template.ui.theme.TemplateTheme
import com.keshogroup.template.ui.viewmodels.ActivityDiagnosticsViewModel
import com.keshogroup.template.ui.viewmodels.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun LoginView(
    activityDiagnosticsViewModel: ActivityDiagnosticsViewModel = viewModel(viewModelStoreOwner = (LocalContext.current as Fragment).requireActivity()),
    name: String,
    modifier: Modifier = Modifier,
    onConfirmClick: (destination: String) -> Unit,
    onCancelClick: (destination: String) -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {


    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false,
            usePlatformDefaultWidth = false,//for full screen set to false
            //turn this on to prevent sensitive data from being shared, screenshot, casted,
            securePolicy = SecureFlagPolicy.SecureOn
        )
    ) {
        val coroutineScope: CoroutineScope = rememberCoroutineScope()
        val state by loginViewModel.loginStateFlow.collectAsStateWithLifecycle()
//        val tickerStateFlow = loginViewModel.tickerStateFlow.collectAsStateWithLifecycle()
        //Creates a new view model every time this composable is created
        val activityVM: ActivityDiagnosticsViewModel = viewModel()
        //Grabs already created view model from the activity every time this composable is created. This is good if you do not want to constantly pass in the activity viewmodel as a parameter
//        val activityVM: ActivityDiagnosticsViewModel = viewModel(viewModelStoreOwner = (LocalContext.current as Fragment).requireActivity())

        val tickerState =
            activityDiagnosticsViewModel.tickerStateFlow2.collectAsStateWithLifecycle()


        //do something with state
        state.intialstate
        val tickerStateValue: Response<Ticker5Min> = tickerState.value
        var tickerText: String = ""
        when (tickerStateValue) {
            is Response.Initial<Ticker5Min> -> {
                tickerText = "initializing"
            }

            is Response.Error<*> -> tickerText = tickerStateValue.message
            is Response.Loading<Ticker5Min> -> {
                tickerText = "loading"
            }

            is Response.Success<Ticker5Min> -> {
                tickerText = tickerStateValue.data.metaData.the1Information
            }
        }
//// Practicing with db
        var tickerFromDbValue = loginViewModel.getTickerAsFlowFromDB().collectAsStateWithLifecycle(
            initialValue = Ticker5Min.empty()
        ).value

        Box(
            modifier
                .background(Color.Cyan)
                .fillMaxSize()
        ) {
            Column(
                modifier
                    .background(Color.Cyan)
                    .fillMaxSize()
            ) {
                Text(
                    text = "LoginView",
                    modifier = modifier
                )
                Text(
                    text = "ticker information ${tickerText}",
                    modifier = Modifier
                )
                Text(
                    text = "ticker from DB Value ${tickerFromDbValue.metaData.the3LastRefreshed}",
                    modifier = Modifier
                )

                Button(
                    onClick = { onConfirmClick(MainDestinations.HOME.route) },
                    modifier = Modifier
                ) {
                    Text(
                        text = "Confirm",
                        modifier = Modifier
                    )
                }
                Button(
                    onClick = {

                        coroutineScope.launch {

                            loginViewModel.getTickerAsFlow("PDD").collect { it ->
                                var results: Response<Ticker5Min> = Response.Initial<Ticker5Min>()
                                results = it as Response<Ticker5Min>
                                when (results) {
                                    is Response.Error<*> -> Log.i(
                                        "Carmen",
                                        "LoginView: Error ${results.message}"
                                    )

                                    is Response.Initial<*> -> Log.i("Carmen", "LoginView: initial")
                                    is Response.Loading<*> -> Log.i("Carmen", "LoginView: loading")
                                    is Response.Success<Ticker5Min> -> Log.i(
                                        "Carmen",
                                        "LoginView: Success ${results.data.metaData.the2Symbol}"
                                    )
                                }

                            }
                        }
                    },
                    modifier = Modifier
                ) {
                    Text(
                        text = "Click here",
                        modifier = Modifier
                    )
                }

                Button(
                    onClick = {

                        coroutineScope.launch {
                            loginViewModel.saveTicker()
                        }
                    },
                    modifier = Modifier
                ) {
                    Text(
                        text = "save to db",
                        modifier = Modifier
                    )
                }

                Button(
                    onClick = { onCancelClick(MainDestinations.HOME.route) },
                    modifier = Modifier
                ) {
                    Text(
                        text = "Cancel",
                        modifier = Modifier
                    )
                }
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun viewPreview() {
    TemplateTheme {
        LoginView(name = "LoginView", onConfirmClick = {}, onCancelClick = {})
    }
}