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
import com.yaabelozerov.holodos_mobile.data.CreateProductDTO
import com.yaabelozerov.holodos_mobile.data.CreateUserDTO
import com.yaabelozerov.holodos_mobile.data.HolodosResponse
import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.data.Owner
import com.yaabelozerov.holodos_mobile.data.Sku
import com.yaabelozerov.holodos_mobile.data.UserDTO
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.Locale

fun CreateUserDTO.toOwner(): Owner = Owner(
    id, firstName, lastName, phone, holodoses, role
)

@Composable
fun AddWidget(onSave: (CreateProductDTO) -> Unit, holodos: HolodosResponse, user: CreateUserDTO, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        var item by remember { mutableStateOf(CreateProductDTO(null, sku = null, holodos, 0, "2024-10-13T09:40:06.084+00:00", user.toOwner())) }

        val df = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val days = df.parse(item.dateMade!!.split("T").first()).toInstant()
        days.plusSeconds((item.sku?.bestBeforeDays ?: 0).toLong() * 24 * 60 * 60)
        val expiryDate = LocalDateTime.ofInstant(days, java.util.TimeZone.getDefault().toZoneId())
        val now = LocalDateTime.now()
        var daysUntilExpiry by remember { mutableStateOf(ChronoUnit.DAYS.between(now, expiryDate)) }.also { Log.i("days", it.value.toString()) }

        var text by remember { mutableStateOf(item.sku?.name ?: "") }

        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.askAdd), style = TextStyle(
                        fontSize = 24.sp
                    ), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
                )
                Row {
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text(stringResource(R.string.productName)) },
                        modifier = Modifier.padding(2.dp),
                        singleLine = true
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Button(onClick = {
                            daysUntilExpiry += 1
                        }) { Icon(Icons.Filled.KeyboardArrowUp, contentDescription = null) }
                        Button(
                            onClick = {
                                daysUntilExpiry += 1
                            }, enabled = daysUntilExpiry > 0
                        ) { Icon(Icons.Filled.KeyboardArrowDown, contentDescription = null) }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = daysUntilExpiry.toString(),
                        onValueChange = {
                            daysUntilExpiry = (it.toIntOrNull() ?: 0).toLong()
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
                        onSave(item.copy(dateMade = now.minusDays(daysUntilExpiry).toString(), sku = item.sku?.copy(name = text) ?: Sku(name = text)))
                        onDismissRequest()
                    }) { Text(stringResource(R.string.save)) }
                }
            }
        }
    }
}