package com.keshogroup.template.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.keshogroup.template.ui.screens.HomeView
import com.keshogroup.template.ui.screens.aboutUs
import com.keshogroup.template.ui.screens.contactUs
import com.keshogroup.template.ui.screens.login.LoginView
import com.keshogroup.template.ui.screens.settingsViewF
import com.keshogroup.template.ui.viewmodels.ActivityDiagnosticsViewModel
import com.keshogroup.template.ui.viewmodels.LoginViewModel

@Preview
@Composable
fun mainNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    activityDiagnosticsViewModel: ActivityDiagnosticsViewModel
) {
    var onClick: (destination: String) -> Unit = {
        navController.navigate(it)
    }
    NavHost(
        navController = navController,
        startDestination = MainDestinations.HOME.route,
        modifier = modifier
    ) {
        composable(route = MainDestinations.HOME.route) {
            HomeView(modifier, navController, onConfirmClick = onClick, onCancelClick = onClick)

        }

        composable(route = MainDestinations.SETTINGS.route) {
            settingsViewF(
                "settings", modifier.padding(40.dp),
                onCancelClick = onClick,
                onConfirmClick = onClick,
            )

        }

        composable(route = MainDestinations.CONTACT_US.route) {
            contactUs(
                "contactUs", modifier.padding(40.dp),
                onCancelClick = onClick,
                onConfirmClick = onClick,
            )

        }

        composable(route = MainDestinations.ABOUT_US.route) { backStackEntry ->
            //Eample of how to access a viewmodel in navigation
            // Retrieve the NavBackStackEntry of "parentNavigationRoute"
            val parentEntry: NavBackStackEntry = remember(backStackEntry) {
                navController.getBackStackEntry("parentNavigationRoute")
            }
            // Get the ViewModel scoped to the `parentNavigationRoute` Nav graph
            val parentViewModel: LoginViewModel = viewModel(parentEntry)
            // ... pass in the vm or do something
            aboutUs(
                "aboutUs", modifier.padding(40.dp),
                onCancelClick = onClick,
                onConfirmClick = onClick,
            )

        }
        dialog(route = MainDestinations.LOGIN.route) {
            LoginView(
                activityDiagnosticsViewModel,
                "Login View", modifier.padding(40.dp),
                onCancelClick = onClick,
                onConfirmClick = onClick,
            )

        }
    }
}