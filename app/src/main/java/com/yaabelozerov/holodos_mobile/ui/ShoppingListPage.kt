package com.yaabelozerov.holodos_mobile.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yaabelozerov.holodos_mobile.data.ItemDTO
import com.yaabelozerov.holodos_mobile.data.SkuDTO
import com.yaabelozerov.holodos_mobile.domain.SettingsScreenViewModel

@Composable
fun ShoppingListPage(
    cartItems: List<SkuDTO>,
    settingsViewModel: SettingsScreenViewModel,
) {

    val _canEdit = settingsViewModel.userIsChild.collectAsState().value

    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn (
            modifier = Modifier.padding(16.dp)
        ) {
            items(cartItems) {
                Item(it.name, it.pictureURL, it.quantity, it.bestBefore)
                Spacer(Modifier.size(8.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Предложения", // TODO String res
                modifier = Modifier
                    .padding(16.dp, 4.dp, 8.dp, 4.dp),
            )
            Box(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .height(5.dp)
                    .weight(1f)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))

            )
        }
        if (_canEdit) {
            LazyColumn (
                modifier = Modifier.padding(16.dp)
            ) {
                items(cartItems) {
                    Item(it.name, it.pictureURL, it.quantity, it.bestBefore)
                    Spacer(Modifier.size(8.dp))
                }
            }
        }
    }

}


@Composable
fun Item (
    name: String,
    imageURL: String?,
    quantity: Int,
    bestBefore: Int
) {
    var item by remember { mutableStateOf(SkuDTO(-1, name, imageURL, quantity, bestBefore = bestBefore)) }
    Card(
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = name,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = {
                item = item.copy(quantity = item.quantity - 1)
            }, enabled = item.quantity > 0) { Icon(Icons.Filled.KeyboardArrowDown, contentDescription = null) }

            Spacer(modifier = Modifier.size(2.dp))

            Text(
                text = item.quantity.toString()
            )

            Spacer(modifier = Modifier.size(2.dp))

            IconButton(
                onClick = {
                    item = item.copy(quantity = item.quantity + 1)
                }
            ) { Icon(Icons.Filled.KeyboardArrowUp, contentDescription = null) }

        }
    }
}


@Composable
fun ItemSuggest (
    name: String,
    imageURL: String?,
    quantity: Int,
    bestBefore: Int
) {
    var item by remember { mutableStateOf(SkuDTO(-1, name, imageURL, quantity, bestBefore = bestBefore)) }
    Card(
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = name,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = item.quantity.toString()
            )
            Spacer(modifier = Modifier.size(2.dp))

            IconButton(onClick = {
                // TODO написать запросы на бэк и прописать их тут
            }, enabled = item.quantity > 0) { Icon(Icons.Filled.Check, contentDescription = null) }

            Spacer(modifier = Modifier.size(2.dp))

            IconButton(onClick = {
                // TODO написать запросы на бэк и прописать их тут
            }, enabled = item.quantity > 0) { Icon(Icons.Filled.Clear, contentDescription = null) }

        }
    }
}