package com.uflow.uflow.ui.presentation.tasks.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.outlined.DeleteSweep
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarInTaskView(
    onClickFilter: () -> Unit,
    onClickDeleteAll: () -> Unit
) {
    var isDialogOpen by remember { mutableStateOf(false) }

    TopAppBar(
        actions = {
            IconButton(onClick = { onClickFilter() }) {
                Icon(imageVector = Icons.Filled.FilterList, contentDescription = null)
            }
            IconButton(onClick = { isDialogOpen = true }) {
                Icon(imageVector = Icons.Default.DeleteSweep, contentDescription = "BorrarTodo")
            }
        },
        title = {
            Text(text = "Todo List")
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    )
    ShowDialogDeleteAll(
        isDialogOpen= isDialogOpen,
        onDismiss = {
            isDialogOpen = false
        },
        onNoClicked = {
            isDialogOpen = false
        },
        onYesClicked = {
            onClickDeleteAll()
            isDialogOpen = false
        }
    )
}

@Composable
fun ShowDialogDeleteAll(
    isDialogOpen: Boolean,
    onDismiss: () -> Unit,
    onNoClicked: () -> Unit,
    onYesClicked: () -> Unit,
) {
    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                Row {
                    TextButton(onClick = { onNoClicked() }) {
                        Text(text = "No")
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    TextButton(onClick = { onYesClicked() }) {
                        Text(text = "Si")
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
                    text = "Se borraran todas las tareas, quieres borrarlas?",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
    }
}