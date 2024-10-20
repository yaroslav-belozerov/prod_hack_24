package com.yaabelozerov.holodos_mobile.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yaabelozerov.holodos_mobile.R


@Composable
fun AuthPage(
    modifier: Modifier, onLogin: (String) -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var login by remember { mutableStateOf("") }
    var isPhoneErr by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(R.string.holodos), fontSize = 64.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(64.dp))
        PhoneField(isErr = isPhoneErr, modifier = Modifier.padding(32.dp, 0.dp), onContinue = {
            if (login.length == 12) {
                isLoading = true
                onLogin(login)
                isPhoneErr = false
                true
            } else {
                isPhoneErr = true
                false
            }
        }, mask = "000 000 00-00", onChange = {
            login = it
            isPhoneErr = false
        })
        Spacer(
            Modifier.size(16.dp)
        )
        if (!isLoading) Button(
            onClick = {
                if (login.length == 12) {
                    isLoading = true
                    onLogin(login)
                    isPhoneErr = false
                } else {
                    isPhoneErr = true
                }
            },
        ) {
            Text(
                text = stringResource(R.string.auth)
            )
        } else {
            CircularProgressIndicator()
        }
    }
}