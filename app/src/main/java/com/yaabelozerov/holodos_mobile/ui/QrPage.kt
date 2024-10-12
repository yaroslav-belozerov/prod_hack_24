package com.yaabelozerov.holodos_mobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QrPage(
    hasPermission: Boolean,
    onSend: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ScanQR(hasPermission) {
            text = it
        }
        Text(
            text = text,
            fontSize = 18.sp
        )
        Button(
            onClick = { onSend(text) }
        ) {
            Text(
                text = "Отправить" // TODO String res
            )
        }
    }
}