package com.yaabelozerov.holodos_mobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yaabelozerov.holodos_mobile.R
import com.yaabelozerov.holodos_mobile.data.UserDTO
import com.yaabelozerov.holodos_mobile.mock.MockApi

@Composable
fun AuthPage(
    modifier: Modifier,
    onLogin: (String) -> Unit
) {
    var login by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = login,
            onValueChange = {login = it},
            label = { Text(stringResource(R.string.askPhoneNum)) }
        )
        Spacer(
            Modifier.size(16.dp)
        )
        Button(
            onClick = { onLogin(login) },

        ) {
            Text(
                text = stringResource(R.string.auth)
            )
        }
    }
}