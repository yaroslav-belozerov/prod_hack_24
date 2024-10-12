package com.yaabelozerov.holodos_mobile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.yaabelozerov.holodos_mobile.Avatars
import com.yaabelozerov.holodos_mobile.R
import com.yaabelozerov.holodos_mobile.data.UserDTO
import com.yaabelozerov.holodos_mobile.domain.SettingsScreenViewModel

@Composable
fun SettingsPage(settingsViewModel: SettingsScreenViewModel, users: List<UserDTO>, onAddUser: (UserDTO) -> Unit) {
    var notificationsOn by remember { mutableStateOf(true) }
    var createDialogOpen by remember { mutableStateOf(false) }
    Column(
        Modifier.padding(16.dp)
    ) {
        LazyRow(
            modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            items(users) {
                AvatarContainer(
                    settingsViewModel.currentId.collectAsState().value,
                    { settingsViewModel.updateUser(it) },
                    it
                )
            }
            item {
                Box(modifier = Modifier
                    .clip(CircleShape)
                    .clickable { createDialogOpen = true }
                    .size(64.dp)) {
                    Icon(
                        Icons.Filled.AddCircle,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    )
                }
            }

        }
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { notificationsOn = !notificationsOn }) {
            Text(
                text = stringResource(R.string.notification),
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp)

            )
            Spacer(
                Modifier.weight(1f)
            )
            Switch(
                checked = notificationsOn, onCheckedChange = {
                    notificationsOn = it
                }, modifier = Modifier.padding(16.dp)

            )
        }
    }

    var newData by remember { mutableStateOf(UserDTO(-1, "", "", "", 0)) }
    if (createDialogOpen) Dialog(
        onDismissRequest = { createDialogOpen = false },
    ) {
        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Avatars.entries.map {
                        Card(
                            onClick = { newData = newData.copy(avatarIndex = it.ordinal) },
                            modifier = Modifier.border(
                                2.dp,
                                if (it.ordinal == newData.avatarIndex) MaterialTheme.colorScheme.primary else Color.Transparent,
                                MaterialTheme.shapes.medium
                            )
                        ) {
                            Image(
                                painterResource(it.res), null,
                                Modifier
                                    .padding(16.dp)
                                    .size(32.dp)
                            )
                        }
                    }
                }
                OutlinedTextField(
                    newData.name,
                    { newData = newData.copy(name = it) },
                    Modifier.padding(16.dp),
                    singleLine = true
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = {
                        createDialogOpen = false
                    }) { Text(stringResource(R.string.cancel)) }
                    Button(modifier = Modifier.weight(1f), onClick = {
                        onAddUser(newData)
                        createDialogOpen = false
                    }) {
                        Text(
                            stringResource(R.string.save)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AvatarContainer(currentId: Long?, onSave: (UserDTO) -> Unit, data: UserDTO) {
    // TODO ux вы, блокировать едит других юзеров
    var openDialog by remember { mutableStateOf(false) }
    var newData by remember {
        mutableStateOf(data)
    }
    if (openDialog) Dialog(onDismissRequest = { openDialog = false }) {
        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Avatars.entries.map {
                        Card(
                            onClick = { newData = newData.copy(avatarIndex = it.ordinal) },
                            modifier = Modifier.border(
                                2.dp,
                                if (it.ordinal == newData.avatarIndex) MaterialTheme.colorScheme.primary else Color.Transparent,
                                MaterialTheme.shapes.medium
                            )
                        ) {
                            Image(
                                painterResource(it.res), null,
                                Modifier
                                    .padding(16.dp)
                                    .size(32.dp)
                            )
                        }
                    }
                }
                OutlinedTextField(
                    newData.name,
                    { newData = newData.copy(name = it) },
                    Modifier.padding(16.dp),
                    singleLine = true
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = {
                        openDialog = false
                    }) { Text(stringResource(R.string.cancel)) }
                    Button(modifier = Modifier.weight(1f), onClick = {
                        onSave(newData)
                        openDialog = false
                    }) {
                        Text(
                            stringResource(R.string.save)
                        )
                    }
                }
            }
        }
    }
    Column(modifier = Modifier
        .clip(MaterialTheme.shapes.medium)
        .border(
            2.dp,
            if (currentId == data.id) MaterialTheme.colorScheme.primary else Color.Transparent,
            MaterialTheme.shapes.medium
        )
        .then(if (currentId == data.id) Modifier.clickable { openDialog = true }
        else Modifier)
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Image(
            modifier = Modifier.size(64.dp),
            painter = painterResource(Avatars.entries[data.avatarIndex].res),
            contentDescription = null
        )
        Text(data.name, fontSize = 20.sp)
    }
}