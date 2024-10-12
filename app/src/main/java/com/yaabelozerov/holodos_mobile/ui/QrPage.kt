package com.yaabelozerov.holodos_mobile.ui

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yaabelozerov.holodos_mobile.R

fun String.validQR(): Boolean {
    val reg = Regex("t=.*s=.*fn=.*i=.*fp=.*n=.*")
    return reg.matches(this)
}

@Composable
fun QrPage(
    hasPermission: Boolean, onSend: (String) -> Unit
) {
    val invalidStr = stringResource(R.string.invalid_qr)
    var text by remember { mutableStateOf(invalidStr) }
    Box(modifier = Modifier.fillMaxSize()) {
        val validStr = stringResource(R.string.valid_qr)
        ScanQR(modifier = Modifier.padding(32.dp).border(4.dp, if (text.validQR()) MaterialTheme.colorScheme.primary else Color.Transparent, MaterialTheme.shapes.medium).clip(MaterialTheme.shapes.medium), hasPermission, onScan = {
            text = it
        })
        Column(
            Modifier.align(Alignment.BottomCenter).padding(0.dp, 0.dp, 0.dp, 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (text.validQR()) validStr else invalidStr, fontSize = 18.sp
            )
            Button(onClick = { onSend(text) }, enabled = text.validQR()) {
                Text(
                    text = stringResource(R.string.fill_in)
                )
            }
        }
    }
}