package com.yaabelozerov.holodos_mobile.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class Navigation(val route: String, val filled: ImageVector, val outlined: ImageVector) {
    SETTINGS("Settings", Icons.Filled.Settings, Icons.Outlined.Settings), FRIDGE(
        "Fridge", Icons.Filled.Home, Icons.Outlined.Home
    ),
    LIST("List", Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart)
}