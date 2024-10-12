package com.yaabelozerov.holodos_mobile.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.yaabelozerov.holodos_mobile.R

enum class Navigation(var route: String, val filled: ImageVector, val outlined: ImageVector, val title: Int, val showInNavBar: Boolean = true) {
    SETTINGS("Settings", Icons.Filled.Settings, Icons.Outlined.Settings, R.string.settings),
    FRIDGE(
        "Fridge", Icons.Filled.Home, Icons.Outlined.Home, R.string.fridge
    ),
    LIST("List", Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart, R.string.cart),
    AUTH("Auth", Icons.Default.Person, Icons.Outlined.Person, R.string.auth, false)
}