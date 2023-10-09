package com.uflow.uflow.ui.navigation.components

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.uflow.uflow.ui.presentation.tasks.add_edit_task.AddEditActivity
import com.uflow.uflow.ui.presentation.work_tasks.add_edit_work_task.AddEditWorkTask
import com.uflow.uflow.ui.theme.PurpleGrey40

@SuppressLint("UnrememberedMutableState")
@Composable
fun FloatingButtonNavigation(
) {
    val isDialogOpen = remember { mutableStateOf(true) }
    val isClicked = remember { mutableStateOf(false) }
    val context = LocalContext.current

    FloatingActionButton(
        onClick = {
            isClicked.value = true
            isDialogOpen.value = true
        },
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Agregar todo o tarea academica")
    }
    if (isClicked.value) {
        DialogContainer(isDialogOpen = isDialogOpen, context)
    }
}


@Composable
fun DialogContainer(
    isDialogOpen: MutableState<Boolean>,
    context: Context
) {
    if (isDialogOpen.value) {
        Dialog(
            onDismissRequest = { isDialogOpen.value = false },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = true)
        ) {
            DialogOptionBetweenTaskAndWorkTask(isDialogOpen = isDialogOpen, context)
        }
    }
}

@Composable
fun DialogOptionBetweenTaskAndWorkTask(
    isDialogOpen: MutableState<Boolean>,
    context: Context
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        //shape = RoundedCornerShape(10.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Column(
            //modifier.background(Color.White)
        ) {

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Que Quieres Hacer?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(0.dp),
                        onClick = {
                            context.startActivity(Intent(context, AddEditActivity::class.java))
                            isDialogOpen.value = false
                        }
                    ) {
                        Row {
                            Icon(imageVector = Icons.Filled.Check , contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Agregar Todo",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(top = 10.dp, start = 25.dp, end = 25.dp),
                                    style = MaterialTheme.typography.titleSmall,

                                    )
                                Text(
                                    text = "Una tarea Todo, la cual podras hacer con la tecnica pomodoro que esta en la seccion Timer",
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .padding(top = 10.dp, start = 25.dp, end = 25.dp),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer

                                )
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(0.dp),
                        onClick = {
                            context.startActivity(Intent(context, AddEditWorkTask::class.java))
                            isDialogOpen.value = false
                        }
                    ) {
                        Row {
                            Icon(imageVector = Icons.Filled.Work , contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Agregar Tarea Academica",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(top = 10.dp, start = 25.dp, end = 25.dp),
                                    style = MaterialTheme.typography.titleSmall,

                                )
                                Text(
                                    text = "Una tarea academica a la cual le puedes poner recordatorios de cuando hacerla y de la fecha de entrega",
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .padding(top = 10.dp, start = 25.dp, end = 25.dp),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer

                                )
                            }
                        }
                    }
                }

            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                horizontalArrangement = Arrangement.SpaceAround,

                ) {

                TextButton(
                    onClick = {
                        isDialogOpen.value = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        "Cerrar",
                        fontWeight = FontWeight.Bold,
                        //color = PurpleGrey40,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }

        }
    }
}