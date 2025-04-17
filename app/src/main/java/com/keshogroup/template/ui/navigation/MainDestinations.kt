package com.keshogroup.template.ui.navigation

import android.graphics.drawable.Icon
import androidx.annotation.IntDef
import androidx.annotation.StringDef
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class MainDestinations(
    val route: String,
    val display: String,
    val icon: ImageVector,
    val iconUnselected: ImageVector
) {
    HOME(route = "HOMEVIEW", display = "home", icon= Icons.Filled.Home, iconUnselected = Icons.Outlined.Home ),
    CONTACT_US(route = "CONTACTUSVIEW", display = "contact", icon= Icons.Filled.Call, iconUnselected = Icons.Outlined.Call ),
    SETTINGS(route = "SETTINGSVIEW", display = "setting", icon= Icons.Filled.Settings, iconUnselected = Icons.Outlined.Settings ),
    LOGIN(route = "LOGINVIEW", display = "Login", icon= Icons.Filled.Face, iconUnselected = Icons.Outlined.Face ),
    ABOUT_US(route = "ABOUTUSVIEW", display = "about", icon= Icons.Filled.Face, iconUnselected = Icons.Outlined.Face ),
}