package com.yaabelozerov.holodos_mobile.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.type.DateTime
import com.yaabelozerov.holodos_mobile.R
import com.yaabelozerov.holodos_mobile.data.CreateProductDTO
import com.yaabelozerov.holodos_mobile.domain.SettingsScreenViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@Composable
fun MainPage(
    settingsVM: SettingsScreenViewModel,
    holodosId: Long,
    products: List<CreateProductDTO>
) {
    val p = products
    LazyColumn(
        modifier = Modifier.padding(16.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(p) { item ->
            Product(item, onAdd = {
                settingsVM.updateProductCount(item, holodosId, item.quantity!! + 1)
            }, onRemove = {
                settingsVM.updateProductCount(item, holodosId, item.quantity!! - 1)
            })
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun Product(
    item: CreateProductDTO,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    val fmt = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSXXX")
    val itemDateMade = LocalDateTime.parse(item.dateMade!!, fmt)
    val staysFreshFor = item.sku?.bestBeforeDays ?: 0
    val daysUntilExpiry = ChronoUnit.DAYS.between(LocalDateTime.now(), itemDateMade.plusDays(staysFreshFor.toLong() + 1L))

    Card(
        colors = CardDefaults.cardColors()
            .copy(containerColor = if (daysUntilExpiry > 0) MaterialTheme.colorScheme.surfaceContainer else MaterialTheme.colorScheme.errorContainer),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp, 32.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 3,
                text = item.sku?.name ?: "",
                fontSize = 24.sp
            )
            Spacer(
                Modifier.width(8.dp)
            )
            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = item.quantity.toString(),
                        fontSize = 20.sp,
                        color = if (daysUntilExpiry > 0) MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f) else MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "×",
                        fontSize = 20.sp,
                        color = if (daysUntilExpiry > 0) MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f) else MaterialTheme.colorScheme.onBackground,
                    )
                    Spacer(Modifier.width(4.dp))
                    OutlinedIconButton(onClick = { onRemove() }) { Icon(painterResource(R.drawable.remove), contentDescription = null) }
                    OutlinedIconButton(onClick = { onAdd() }) { Icon(Icons.Default.Add, contentDescription = null) }
                }
                Text(
                    text = if (daysUntilExpiry > 0) daysUntilExpiry.toString() + " " + stringResource(R.string.days) else stringResource(
                        R.string.expired
                    ),
                    fontSize = 20.sp
                )
            }
        }
    }
}