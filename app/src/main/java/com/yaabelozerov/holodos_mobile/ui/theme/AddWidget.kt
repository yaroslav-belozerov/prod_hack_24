package com.yaabelozerov.holodos_mobile.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.yaabelozerov.holodos_mobile.R

@Composable
fun AddWidget(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        var productName by remember { mutableStateOf("") }
        var daysLeft by remember { mutableStateOf("") }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(32.dp),
        ) {
            Column (
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.askAdd),
                    style = TextStyle(
                        fontSize = 18.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Row {
                    TextField(
                        value = productName,
                        onValueChange = { productName = it },
                        label = { Text(stringResource(R.string.productName)) }
                    )
                }
                Spacer(Modifier.size(32.dp))
                Row {
                    TextField(
                        value = daysLeft,
                        onValueChange = { daysLeft = it },
                        label = { Text(stringResource(R.string.BestBeforeDays)) }
                    )
                }
            }
        }
    }
}