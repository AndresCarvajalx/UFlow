package com.uflow.uflow.ui.presentation.setting

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun SettingScreen(
    navController: NavController,
    isDarkTheme: MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(text = "Testing", fontSize = 30.sp)
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .align(Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Modo Oscuro"
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .align(Alignment.CenterVertically),
                horizontalAlignment = Alignment.End
            ) {
                Switch(
                    checked = isDarkTheme.value,
                    onCheckedChange = {
                        isDarkTheme.value = !isDarkTheme.value
                    },
                )
            }

        }
    }
}