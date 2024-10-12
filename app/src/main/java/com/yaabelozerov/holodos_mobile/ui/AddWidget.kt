package com.yaabelozerov.holodos_mobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.yaabelozerov.holodos_mobile.R
import com.yaabelozerov.holodos_mobile.data.ItemDTO

@Composable
fun AddWidget(onSave: (ItemDTO) -> Unit, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        var item by remember { mutableStateOf(ItemDTO(-1, "", 0, 1)) }
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.askAdd), style = TextStyle(
                        fontSize = 24.sp
                    ), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
                )
                Spacer(Modifier.size(8.dp))
                Row {
                    OutlinedTextField(
                        value = item.name,
                        onValueChange = { item = item.copy(name = it) },
                        label = { Text(stringResource(R.string.productName)) },
                        modifier = Modifier.padding(2.dp),
                        singleLine = true
                    )
                }
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Button(onClick = {
                            item = item.copy(daysUntilExpiry = item.daysUntilExpiry + 1)
                        }) { Icon(Icons.Filled.KeyboardArrowUp, contentDescription = null) }
                        Button(
                            onClick = {
                                item = item.copy(daysUntilExpiry = item.daysUntilExpiry - 1)
                            }, enabled = item.daysUntilExpiry > 0
                        ) { Icon(Icons.Filled.KeyboardArrowDown, contentDescription = null) }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = item.daysUntilExpiry.toString(),
                        onValueChange = {
                            item = item.copy(daysUntilExpiry = it.toIntOrNull() ?: 0)
                        },
                        label = { Text(stringResource(R.string.BestBeforeDays)) },
                        modifier = Modifier.padding(2.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = onDismissRequest) { Text(stringResource(R.string.cancel)) }
                    Button(modifier = Modifier.weight(1f), onClick = {
                        onSave(item)
                        onDismissRequest()
                    }) { Text(stringResource(R.string.save)) }
                }
            }
        }
    }
}