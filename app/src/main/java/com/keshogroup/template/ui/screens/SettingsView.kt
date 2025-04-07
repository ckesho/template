package com.keshogroup.template.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.keshogroup.template.ui.navigation.MainDestinations
import com.keshogroup.template.ui.theme.TemplateTheme

class SettingsView {
}

@Composable
fun settingsViewF(name: String, modifier: Modifier = Modifier, onCancelClick:(destination:String)->Unit, onConfirmClick: (destination:String)->Unit) {
    Column(modifier){
        Text(
            text = "settingsViewF",
            modifier = modifier
        )
        Button(onClick ={ onConfirmClick(MainDestinations.HOME.route)},
            modifier = modifier){
            Text(
                text = "Confirm",
                modifier = modifier
            )
        }
        Button(onClick ={ onCancelClick(MainDestinations.HOME.route)},
            modifier = modifier){
            Text(
                text = "Cancel",
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun settingsViewFPreview() {
    TemplateTheme {
        settingsViewF("SettingsView", onCancelClick = {}, onConfirmClick = {})
    }
}