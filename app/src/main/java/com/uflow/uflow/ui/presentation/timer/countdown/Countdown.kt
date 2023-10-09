package com.uflow.uflow.ui.presentation.timer.countdown

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Countdown(
    context: Context,
    modifier: Modifier = Modifier,
    viewModel: CountdownViewModel = hiltViewModel()
) {
    viewModel.createMediaPlayer(context = context)
    val isDialogOpen = remember { mutableStateOf(false) }
    val studyTime = remember { mutableStateOf(0) }
    val breakTime = remember { mutableStateOf(0) }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        AnimatedVisibility(visible = isDialogOpen.value) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = if (studyTime.value == 0){""}else{studyTime.value.toString()},
                    onValueChange = { },
                    placeholder = {
                        Text(text = "Tiempo de Estudio (minuntos)")
                    },
                    readOnly = true,
                    leadingIcon = {
                        IconButton(onClick = {
                            if(studyTime.value > 0){
                                studyTime.value = studyTime.value - 5
                            }
                        }
                        ) {
                            Icon(imageVector = Icons.Outlined.Remove, contentDescription = null)

                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            if(studyTime.value < 180){
                                studyTime.value  = studyTime.value  + 10
                            }
                        }) {
                            Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                        }
                    },
                    textStyle = TextStyle(textAlign = TextAlign.Center)
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = if (breakTime.value == 0){""}else{breakTime.value.toString()},
                    onValueChange = { },
                    placeholder = {
                        Text(text = "Tiempo de Descanso (minuntos)")
                    },
                    readOnly = true,
                    leadingIcon = {
                        IconButton(onClick = {
                            if(breakTime.value > 0){
                                breakTime.value = breakTime.value - 5
                            }
                        }
                        ) {
                            Icon(imageVector = Icons.Outlined.Remove, contentDescription = null)

                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            if(breakTime.value < 180){
                                breakTime.value  = breakTime.value  + 10
                            }
                        }) {
                            Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                        }
                    },
                    textStyle = TextStyle(textAlign = TextAlign.Center)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    onClick = {
                        try {
                            viewModel.onEvent(
                                CountdownEvent.OnSave(
                                    breakTime.value,
                                    studyTime.value
                                )
                            )
                        } catch (_: Exception) {
                        }
                        isDialogOpen.value = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(text = "Guardar")
                }
                Spacer(modifier = Modifier.height(1.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    onClick = {
                        viewModel.onEvent(CountdownEvent.OnPomodoroDefault)
                        isDialogOpen.value = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(text = "Pomodoro Default")
                }
            }
        }
    }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = viewModel.reamingText.value,
                fontFamily = FontFamily.Serif,
                style = TextStyle(
                    fontSize = 50.sp,
                )
            )

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (!viewModel.isStarting.value && !viewModel.isPaused.value) {
                            viewModel.onStart()
                            isDialogOpen.value = false
                        } else if (viewModel.isPaused.value) {
                            viewModel.onResume()
                            isDialogOpen.value = false
                        } else {
                            viewModel.onPause()
                            isDialogOpen.value = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Icon(
                        imageVector = if (viewModel.isStarting.value) {
                            Icons.Default.Pause
                        } else {
                            Icons.Default.PlayArrow
                        },
                        contentDescription = null
                    )
                    Text(
                        text = if (viewModel.isStarting.value) {
                            "pause"
                        } else {
                            "Start"
                        }
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                IconButton(
                    onClick = { isDialogOpen.value = !isDialogOpen.value },
                    enabled = !viewModel.isStarting.value
                ) {
                    Icon(
                        imageVector = if (isDialogOpen.value) {
                            Icons.Default.Close
                        } else {
                            Icons.Default.Edit
                        }, contentDescription = null
                    )
                }
                AnimatedVisibility(visible = viewModel.isPaused.value) {
                    Button(
                        onClick = { viewModel.onCancel() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Stop,
                            contentDescription = null
                        )
                        Text(
                            text = "Stop"
                        )
                    }
                }
            }
        }
    }
}