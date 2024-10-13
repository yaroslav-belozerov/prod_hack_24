package com.yaabelozerov.holodos_mobile.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.yaabelozerov.holodos_mobile.R

enum class Navigation(var route: String, val filled: ImageVector? = null, val outlined: ImageVector? = null, val resFilled: Int? = null, val resOutlined: Int? = null, val title: Int, val showInNavBar: Boolean = true) {
    LIST("List", Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart, title =  R.string.cart),
    FRIDGE(
        "Fridge", resFilled = R.drawable.fridge_filled, resOutlined = R.drawable.firdge_outlined, title = R.string.fridge
    ),
    SETTINGS("Group", Icons.Filled.Person, Icons.Outlined.Person, title=  R.string.account),
    AUTH("Auth", Icons.Default.Person, Icons.Outlined.Person, title = R.string.auth, showInNavBar = false),
    SCAN("Scan", Icons.Filled.Create, Icons.Outlined.Create, title =  R.string.scan_qr, showInNavBar = false)

}