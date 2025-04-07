package com.keshogroup.template.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.keshogroup.template.ui.navigation.MainDestinations
import com.keshogroup.template.ui.theme.TemplateTheme

@Composable
fun contactUs(name: String, modifier: Modifier = Modifier, onCancelClick:(destination:String)->Unit, onConfirmClick: (destination:String)->Unit) {
    Column(modifier){
        Text(
            text = "contactUs",
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
fun contactUsPreview() {
    TemplateTheme {
        settingsViewF("ContactUs", onCancelClick = {}, onConfirmClick = {})
    }
}