package com.keshogroup.template.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.keshogroup.template.ui.navigation.MainDestinations
import com.keshogroup.template.ui.theme.TemplateTheme
import com.keshogroup.template.ui.viewmodels.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun LoginView(
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
            usePlatformDefaultWidth = true,
            //turn this on to prevent sensitive data from being shared, screenshot, casted,
            securePolicy = SecureFlagPolicy.SecureOn
        )
    ) {
        val coroutineScope: CoroutineScope = rememberCoroutineScope()
        val state by loginViewModel.loginStateFlow.collectAsStateWithLifecycle()
        var ticker: String = ""
        var tester: String = ""
        var tickerFlow: Flow<Any> = loginViewModel.tickerFlow
        var tickerFlowtet: String = ""
        var tickerFlowtetV2: String = ""


        //do something with state
        state.getValue()
        Column(modifier) {
            Text(
                text = "LoginView",
                modifier = modifier
            )
            Text(
                text = "ticker $ticker",
                modifier = Modifier
            )
            Text(
                text = "tickerflowtet $tickerFlowtet",
                modifier = Modifier
            )
            Text(
                text = "tester $tester",
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
//                    coroutineScope.launch {
//                        loginViewModel.tickerFlow.collect { it ->
//                            ticker = it.toString()
//                        }
//                    }

//                    coroutineScope.launch {
//                        loginViewModel.getTicker("PDD").collect { it ->
//                            tickerFlowtet = it.toString()
//                        }
//                    }
                    coroutineScope.launch {
                        loginViewModel.getTickerV2("PDD").collect { it ->
                            tickerFlowtetV2 = it.toString()
                        }
                    }
                    tester = "PDD"
                },
                modifier = Modifier
            ) {
                Text(
                    text = "ticker PDD",
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

@Preview(showBackground = true)
@Composable
fun viewPreview() {
    TemplateTheme {
        LoginView("AboutUs", onConfirmClick = { }, onCancelClick = {})
    }
}