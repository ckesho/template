package com.keshogroup.template.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ActivityDiagnosticsState(
    val intialstate:Int = 0,
    val internetConnected: Boolean = false,
);

class ActivityDiagnosticsViewModel :ViewModel(){

    //create a private flow that can change values
    private val _activityStateFlow : MutableStateFlow<ActivityDiagnosticsState> = MutableStateFlow(ActivityDiagnosticsState())
    //use the private flow to create a public flow to be observed and immutable
    val activityStateFlow: StateFlow<ActivityDiagnosticsState> = _activityStateFlow.asStateFlow()

    //functions that notify
    fun internetConnected(){
        _activityStateFlow.update {
            currentLoginState->
            //use copy to make an copy only changing the items you pass through as parameters
            currentLoginState.copy(internetConnected = true)
            //alternatively
            ActivityDiagnosticsState(intialstate = 0, internetConnected = true)
        }
    }
}