package com.uflow.uflow.ui.presentation.work_tasks.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DialogDeleteAllWorkTask(
    isOpen: MutableState<Boolean>,
    onConfirm: () -> Unit
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = { isOpen.value = false },
        confirmButton = {
            Row {
                TextButton(onClick = { isOpen.value = false }) {
                    Text(text = "No")
                }
                Spacer(modifier = Modifier.width(20.dp))
                TextButton(onClick = {
                    onConfirm()
                }) {
                    Text(
                        text = "Si",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        },
        title = {
            Text(
                text = "Alerta",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        text = {
            Text(
                text = "Se borraran todas las tareas academicas, quieres borrarlas?",
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    )
}