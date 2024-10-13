package com.yaabelozerov.holodos_mobile.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.yaabelozerov.holodos_mobile.Avatars
import com.yaabelozerov.holodos_mobile.R
import com.yaabelozerov.holodos_mobile.data.CreateUserDTO
import com.yaabelozerov.holodos_mobile.domain.SettingsScreenViewModel

@Composable
fun SettingsPage(settingsViewModel: SettingsScreenViewModel, users: List<CreateUserDTO>, onAddUser: (String, Boolean) -> Unit) {
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
                    settingsViewModel.current.collectAsState().value!!.id,
                    { new -> settingsViewModel.updateUser(new) },
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

    var phone by remember { mutableStateOf("") }
    var isSponsor by remember { mutableStateOf(true) }
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
                    Text(stringResource(
                        R.string.invite_user
                    ), fontSize = 20.sp)
//                    Avatars.entries.map {
//                        Card(
//                            onClick = { newData = newData.copy(avatarIndex = it.ordinal) },
//                            modifier = Modifier.border(
//                                2.dp,
//                                if (it.ordinal == newData.avatarIndex) MaterialTheme.colorScheme.primary else Color.Transparent,
//                                MaterialTheme.shapes.medium
//                            )
//                        ) {
//                            Image(
//                                painterResource(it.res), null,
//                                Modifier
//                                    .padding(16.dp)
//                                    .size(32.dp)
//                            )
//                        }
//                    }
                }
                OutlinedTextField(
                    phone,
                    { phone = it },
                    Modifier.padding(16.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = { Text(stringResource(R.string.phone_num)) }
                )
                Row(modifier = Modifier.clickable { isSponsor = !isSponsor }, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Checkbox(isSponsor, onCheckedChange = { isSponsor = it })
                    Text(stringResource(R.string.can_add_items))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = {
                        createDialogOpen = false
                    }) { Text(stringResource(R.string.cancel)) }
                    Button(modifier = Modifier.weight(1f), onClick = {
                        onAddUser(phone, isSponsor)
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
fun AvatarContainer(currentId: Long?, onSave: (CreateUserDTO) -> Unit, data: CreateUserDTO) {
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
                    newData.firstName ?: "",
                    { newData = newData.copy(firstName = it) },
                    Modifier.padding(16.dp),
                    singleLine = true
                )
                OutlinedTextField(
                    newData.lastName ?: "",
                    { newData = newData.copy(lastName = it) },
                    Modifier.padding(16.dp),
                    singleLine = true
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = {
                        openDialog = false
                    }) { Text(stringResource(R.string.cancel)) }
                    Button(modifier = Modifier.weight(1f), onClick = {
                        onSave(newData).also { Log.i("newData", newData.toString()) }
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
            painter = painterResource(Avatars.entries[data.avatarIndex ?: 0].res),
            contentDescription = null
        )
        Text(data.firstName ?: "", fontSize = 20.sp)
    }
}