package com.yaabelozerov.holodos_mobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yaabelozerov.holodos_mobile.R
import com.yaabelozerov.holodos_mobile.data.ItemDTO


@Composable
fun MainPage(
    products: List<ItemDTO>
) {
    val p = products
    LazyColumn(
        modifier = Modifier.padding(16.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )  {
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(p) {
             Product(it.name, it.daysUntilExpiry, it.quantity)
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun Product(
    name: String,
    expTime: Int,
    amount: Int,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors().copy(containerColor = if (expTime > 0) MaterialTheme.colorScheme.surfaceContainer else MaterialTheme.colorScheme.errorContainer),
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = amount.toString(),
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
            Spacer(
                Modifier.size(8.dp)
            )
            Text(
                text = name,
                fontSize = 24.sp
            )
            Spacer(
                Modifier.weight(1f)
            )
            Text(
                text = if (expTime > 0) expTime.toString() else stringResource(R.string.expired),
                fontSize = 20.sp
            )
        }
    }
}