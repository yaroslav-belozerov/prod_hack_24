package com.yaabelozerov.holodos_mobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yaabelozerov.holodos_mobile.R
import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.domain.MainScreenViewModel


@Composable
fun MainPage(
    mainScreenViewModel: MainScreenViewModel,
    products: List<ItemDTO>
) {
    val p = products
    LazyColumn(
        modifier = Modifier.padding(16.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ScanQR(true)
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(p) { item ->
            Product(item, onAdd = {
                mainScreenViewModel.incrementProductCount(item.id)
            }, onRemove = {
                mainScreenViewModel.removeItem(item.id)
            })
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun Product(
    item: ItemDTO,
    onAdd: (Long) -> Unit,
    onRemove: (Long) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors()
            .copy(containerColor = if (item.daysUntilExpiry > 0) MaterialTheme.colorScheme.surfaceContainer else MaterialTheme.colorScheme.errorContainer),
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
                maxLines = 2,
                text = item.name,
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
                        color = Color.Gray
                    )
                    Spacer(
                        Modifier.size(2.dp)
                    )
                    Text(
                        text = "×",
                        fontSize = 20.sp,
                        color = Color.Gray,
                    )
                }
                Text(
                    text = if (item.daysUntilExpiry > 0) item.daysUntilExpiry.toString() + " " + stringResource(R.string.days) else stringResource(
                        R.string.expired
                    ),
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedIconButton(onClick = { onAdd(item.id) }) { Icon(Icons.Default.Add, contentDescription = null) }
                OutlinedIconButton(onClick = { onRemove(item.id) }) { Icon(painterResource(R.drawable.remove), contentDescription = null) }
            }
        }
    }
}