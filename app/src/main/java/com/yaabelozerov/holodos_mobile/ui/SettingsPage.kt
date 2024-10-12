package com.yaabelozerov.holodos_mobile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yaabelozerov.holodos_mobile.Avatars
import com.yaabelozerov.holodos_mobile.R

@Composable
fun SettingsPage(avatarInder: Int, name: String) {
    var notificationsOn by remember { mutableStateOf(true) }
    Column {
        LazyRow(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(6) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Image(modifier = Modifier.clip(CircleShape).background(MaterialTheme.colorScheme.surfaceContainer).size(64.dp), painter = painterResource(Avatars.entries[avatarInder].res), contentDescription = null)
                    Text(name, fontSize = 20.sp)
                }
            }
        }
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { notificationsOn = !notificationsOn }
        ) {
            Text(
                text = stringResource(R.string.notification),
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp)

            )
            Spacer(
                Modifier.weight(1f)
            )
            Switch(
                checked = notificationsOn,
                onCheckedChange = {
                    notificationsOn = it
                },
                modifier = Modifier.padding(16.dp)

            )
        }

    }
}