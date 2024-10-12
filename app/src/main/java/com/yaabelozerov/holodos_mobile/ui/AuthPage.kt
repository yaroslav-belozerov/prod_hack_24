package com.yaabelozerov.holodos_mobile.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.yaabelozerov.holodos_mobile.data.UserDTO
import com.yaabelozerov.holodos_mobile.mock.MockApi

@Composable
fun AuthPage(
    onLogin: (String) -> Unit
) {
    var login by remember { mutableStateOf("") }
    Column {
        TextField(
            value = login,
            onValueChange = {login = it}
        )
        Button(
            onClick = { onLogin(login) }
        ) {

        }
    }
}