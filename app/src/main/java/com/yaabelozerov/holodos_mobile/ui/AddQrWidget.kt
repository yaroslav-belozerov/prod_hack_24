package com.yaabelozerov.holodos_mobile.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.yaabelozerov.holodos_mobile.R
import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.data.QRDTO
import com.yaabelozerov.holodos_mobile.domain.RussianNumberVisualTransformation

@Composable
fun Item_(
    name: String?,
    quantity: Int?
) {
    var bestBefore by remember { mutableStateOf(0) }
    Card {
        Row {
            if (name != null) {
                Text(
                    text = name
                )
            }
            Spacer(Modifier.size(8.dp))
            Text(
                text = quantity.toString()
            )
            Spacer(Modifier.weight(1f))
            OutlinedTextField(
                value = bestBefore.toString(),
                onValueChange = {bestBefore = it.toInt()},
                label = {
                    Text(
                        "Срок годности"
                    )
                }

            )

        }
    }
}

@Composable
fun AddQrWidget(
    items: QRDTO,
    onSave: (QRDTO) -> Unit,
    onDismissRequest: () -> Unit
) {

}

