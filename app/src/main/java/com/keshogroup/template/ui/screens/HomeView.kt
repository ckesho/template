package com.keshogroup.template.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.keshogroup.template.ui.navigation.MainDestinations
import com.keshogroup.template.ui.theme.TemplateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(modifier: Modifier = Modifier, navController: NavHostController?) {

        var presses by remember { mutableIntStateOf(0) }
    var selectedItem by remember { mutableIntStateOf(0) }
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        val disTitle = MainDestinations.entries.get(selectedItem).display
                        Text("Top app bar $disTitle")
                    }
                )
            },
            bottomBar = {
                val navigationBarItems = MainDestinations.entries
                NavigationBar (
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                ) {
                    navigationBarItems.forEachIndexed{index, mainDestinations ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    if (selectedItem == index) mainDestinations.icon else mainDestinations.iconUnselected,
                                    contentDescription = mainDestinations.name
                                )
                            },
                            label = { Text(mainDestinations.display) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index
                                navController?.navigate(mainDestinations.route)
                            }
                        )
                    }
//                    Text(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        textAlign = TextAlign.Center,
//                        text = "Bottom app bar",
//                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { presses++ }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) { innerPadding ->

                Greeting(
                    name = "Chris Android template",
                    modifier = Modifier.padding(innerPadding)
                )

        }

}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview(modifier: Modifier = Modifier) {
    TemplateTheme {
        HomeView(modifier, navController = null)
    }
}