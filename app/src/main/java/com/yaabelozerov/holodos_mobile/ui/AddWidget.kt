package com.yaabelozerov.holodos_mobile.ui

import android.util.Log
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
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.yaabelozerov.holodos_mobile.R
import com.yaabelozerov.holodos_mobile.data.CreateProductDTO
import com.yaabelozerov.holodos_mobile.data.CreateUserDTO
import com.yaabelozerov.holodos_mobile.data.HolodosResponse
import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.data.Sku
import com.yaabelozerov.holodos_mobile.data.UserDTO
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.Temporal
import java.time.temporal.TemporalUnit
import java.util.Locale

@Composable
fun AddWidget(
    onSave: (CreateProductDTO) -> Unit,
    holodos: HolodosResponse,
    user: CreateUserDTO,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        val fmt = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSXXX")
        var daysUntilExpiry by remember { mutableIntStateOf(0) }
        var isError by remember { mutableStateOf(false) }

        var item by remember {
            mutableStateOf(
                CreateProductDTO(
                    id = 0,
                    sku = Sku(0, "", pictureUrl = "", bestBeforeDays = 0, products = emptyList()),
                    holodos = holodos.copy(users = listOf(user), products = emptyList()),
                    quantity = 1,
                    dateMade = fmt.format(LocalDateTime.now().atZone(ZoneId.systemDefault())),
                    owner = user
                )
            )
        }

        var text by remember { mutableStateOf(item.sku?.name ?: "") }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.askAdd), style = TextStyle(
                        fontSize = 24.sp
                    ), modifier = Modifier.fillMaxWidth().padding(0.dp, 4.dp, 0.dp, 0.dp), textAlign = TextAlign.Center
                )
                Row {
                    OutlinedTextField(
                        isError = isError,
                        value = text,
                        onValueChange = {
                            text = it
                            isError = false
                        },
                        label = { Text(stringResource(R.string.productName)) },
                        modifier = Modifier.padding(2.dp),
                        singleLine = true
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedIconButton(
                        onClick = {
                            daysUntilExpiry -= 1
                        }, enabled = daysUntilExpiry > 0
                    ) { Icon(Icons.Filled.KeyboardArrowDown, contentDescription = null) }
                    OutlinedTextField(
                        value = daysUntilExpiry.toString(),
                        onValueChange = {
                            daysUntilExpiry = (it.toIntOrNull() ?: 0)
                        },
                        label = { Text(stringResource(R.string.BestBeforeDays)) },
                        modifier = Modifier
                            .padding(2.dp)
                            .weight(1f, false),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    OutlinedIconButton(onClick = {
                        daysUntilExpiry += 1
                    }) { Icon(Icons.Filled.KeyboardArrowUp, contentDescription = null) }
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedIconButton(
                        onClick = {
                            item = item.copy(quantity = (item.quantity ?: 0) - 1)
                        }, enabled = item.quantity != 1
                    ) { Icon(Icons.Filled.KeyboardArrowDown, contentDescription = null) }
                    OutlinedTextField(
                        value = item.quantity.toString(),
                        onValueChange = {
                            item = item.copy(quantity = it.toIntOrNull() ?: 0)
                        },
                        label = { Text(stringResource(R.string.quantity)) },
                        modifier = Modifier
                            .padding(2.dp)
                            .weight(1f, false),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                    OutlinedIconButton(onClick = {
                        item = item.copy(quantity = (item.quantity ?: 0) + 1)
                    }) { Icon(Icons.Filled.KeyboardArrowUp, contentDescription = null) }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(onClick = onDismissRequest) { Text(stringResource(R.string.cancel)) }
                    Button(modifier = Modifier.weight(1f), onClick = {
                        if (text.isEmpty()) {
                            isError = true
                        } else {
                            onSave(
                                item.copy(
                                    sku = item.sku?.copy(
                                        name = text, bestBeforeDays = daysUntilExpiry
                                    ) ?: Sku(name = text, bestBeforeDays = daysUntilExpiry)
                                )
                            )
                            onDismissRequest()
                        }
                    }) { Text(stringResource(R.string.save)) }
                }
            }
        }
    }
}