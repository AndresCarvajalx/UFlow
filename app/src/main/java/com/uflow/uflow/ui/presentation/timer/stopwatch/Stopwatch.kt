package com.uflow.uflow.ui.presentation.timer.stopwatch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Stopwatch(
    model: StopwatchViewModel = hiltViewModel()
) {
    Column {
        Text(text = "Stopwatch")
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "Start")
        }
    }
}