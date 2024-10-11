package com.yaabelozerov.holodos_mobile.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yaabelozerov.holodos_mobile.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.random.Random


@Composable
fun MainPage(
    products: List<Pair<String, Int>>
) {
    val p = products
    Column {
        p.map {
            Product(
                name = it.first,
                expTime = it.second,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun Product(
    name: String,
    expTime: Int,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors().copy(containerColor = if (expTime > 0) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.errorContainer),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row {
            if (expTime > 0) {
                Text(
                    text = name,
                    fontSize = 18.sp,
                    modifier =  Modifier
                        .padding(16.dp)
                )
                Spacer(
                    Modifier.weight(1f)
                )
                Text(
                    text = expTime.toString(),
                    fontSize = 18.sp,
                    modifier =  Modifier
                        .padding(16.dp)
                )

            } else {
                Text(
                    text = name
                )
                Spacer(
                    Modifier.weight(1f)
                )
                Text(
                    text = stringResource(R.string.expired)
                )
            }

        }
    }
}