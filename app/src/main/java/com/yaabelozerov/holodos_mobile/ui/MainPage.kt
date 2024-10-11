package com.yaabelozerov.holodos_mobile.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.yaabelozerov.holodos_mobile.R
import kotlin.random.Random


@Composable
fun MainPage() {

}

@Composable
fun Product(
    name: String,
    expTime: Int,
    expired: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier,
        colors = CardDefaults.cardColors().copy(containerColor = if (expTime > 0) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.errorContainer)
    ) {
        Row {
            if (expTime > 0) {
                Text(
                    text = name
                )
                Spacer(
                    Modifier.weight(1f)
                )
                Text(
                    text = expTime.toString()
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