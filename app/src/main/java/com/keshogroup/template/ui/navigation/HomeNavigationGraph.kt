package com.keshogroup.template.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.keshogroup.template.ui.screens.HomeView
import com.keshogroup.template.ui.screens.aboutUs
import com.keshogroup.template.ui.screens.contactUs
import com.keshogroup.template.ui.screens.settingsViewF
@Preview
@Composable
fun homeNavigationGraph(modifier: Modifier = Modifier,     navController: NavHostController = rememberNavController()
) {
    var onClick: ( destination:String) ->Unit = {
        navController.navigate(it)
    }
    NavHost(
        navController = navController,
        startDestination = MainDestinations.HOME.route,
        modifier = modifier
    ) {
        composable(route = MainDestinations.HOME.route){
            HomeView( modifier, navController, onConfirmClick = onClick, onCancelClick =  onClick)

        }

        composable(route = MainDestinations.SETTINGS.route){
            settingsViewF("settings", modifier.padding(40.dp),
                onCancelClick = onClick,
                onConfirmClick = onClick,)

        }

        composable(route = MainDestinations.CONTACT_US.route){
            contactUs("contactUs", modifier.padding(40.dp),
                onCancelClick = onClick,
                onConfirmClick = onClick,)

        }

        composable(route = MainDestinations.ABOUT_US.route){
            aboutUs("aboutUs", modifier.padding(40.dp),
                onCancelClick = onClick,
                onConfirmClick = onClick,)

        }
    }
}