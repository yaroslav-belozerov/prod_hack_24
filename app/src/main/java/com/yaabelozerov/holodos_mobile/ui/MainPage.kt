package com.yaabelozerov.holodos_mobile.ui

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yaabelozerov.holodos_mobile.R
import com.yaabelozerov.holodos_mobile.ui.theme.AddWidget
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.random.Random


@Composable
fun MainPage(
    products: List<Triple<String, Int, Int>>,
    // onClick: () -> Unit
) {
    val p = products
    var addWidgetOpen by remember { mutableStateOf(false) }
    if (addWidgetOpen) AddWidget {
        addWidgetOpen = false
    }

    LazyColumn(
        modifier = Modifier.padding(16.dp)
    )  {

         items(p) {
             Product(it.first, it.second, it.third)
             Spacer(Modifier.size(8.dp))
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (expTime > 0) {
                Text(
                    text = name,
                    fontSize = 24.sp,
                    modifier =  Modifier
                        .padding(16.dp)
                )
                Text(
                    text = "x",
                    fontSize = 20.sp,
                    color = Color.Gray,

                )
                Spacer(
                    Modifier.size(10.dp)
                )
                Text(
                    text = amount.toString(),
                    fontSize = 20.sp,
                    color = Color.Gray
                )
                Spacer(
                    Modifier.weight(1f)
                )
                Text(
                    text = expTime.toString(),
                    fontSize = 20.sp,
                    modifier =  Modifier
                        .padding(16.dp)
                )
            } else {
                Text(
                    text = name,
                    fontSize = 24.sp,
                    modifier =  Modifier
                        .padding(16.dp)
                )
                Text(
                    text = "x",
                    fontSize = 20.sp,
                    color = Color.Gray,

                    )
                Spacer(
                    Modifier.size(10.dp)
                )
                Text(
                    text = amount.toString(),
                    fontSize = 20.sp,
                    color = Color.Gray
                )
                Spacer(
                    Modifier.weight(1f)
                )
                Text(
                    text = stringResource(R.string.expired),
                    fontSize = 20.sp,
                    modifier =  Modifier
                        .padding(16.dp)
                )
            }

        }
    }
}