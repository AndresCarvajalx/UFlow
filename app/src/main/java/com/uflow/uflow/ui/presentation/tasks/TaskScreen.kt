package com.uflow.uflow.ui.presentation.tasks

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.uflow.uflow.domain.utils.TaskOrder
import com.uflow.uflow.ui.presentation.tasks.add_edit_task.AddEditActivity
import com.uflow.uflow.ui.presentation.tasks.components.TaskItem
import com.uflow.uflow.ui.presentation.tasks.components.TopBarInTaskView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    model: TaskViewModel = hiltViewModel(),
) {
    val state = model.state.value
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = {
            TopBarInTaskView(
                onClickFilter = { model.onEvent(TaskEvent.ToggleOrderSection) },
                onClickDeleteAll = { model.onEvent(TaskEvent.DeleteAllTask)}
            )
        },
        snackbarHost = {scaffoldState.snackbarHostState}
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
            ) {
                Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                    OrderSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        taskOrder = state.taskOrder,
                        onOrderChange = { order ->
                            model.onEvent(TaskEvent.Order(order))
                        }
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding())
            ) {
                items(state.tasks) { task ->
                    Spacer(modifier = Modifier.height(10.dp))
                    TaskItem(
                        task = task,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                context.startActivity(
                                    Intent(
                                        context,
                                        AddEditActivity::class.java
                                    ).putExtra("taskId", task.id)
                                )
                            },
                        onDelete = {
                            scope.launch {
                                model.onEvent(TaskEvent.DeleteTask(task))
                            }
                        },
                        onChangeChecked = {
                            val mtask = task.copy(
                                isComplete = !task.isComplete
                            )
                            scope.launch {
                                model.onEvent(TaskEvent.UpdateTask(mtask, state.taskOrder))
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun OrderSection(
    modifier: Modifier,
    taskOrder: TaskOrder,
    onOrderChange: (TaskOrder) -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center
        ) {
            MyRadioButton(
                text = "Completas",
                selected = taskOrder == TaskOrder.Completes,
                onSelect = { onOrderChange(TaskOrder.Completes) }
            )
            Spacer(modifier = Modifier.width(10.dp))
            MyRadioButton(
                text = "Fecha",
                selected = taskOrder == TaskOrder.Date,
                onSelect = { onOrderChange(TaskOrder.Date) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            MyRadioButton(
                text = "NoCompletas",
                selected = taskOrder == TaskOrder.NoCompletes,
                onSelect = { onOrderChange(TaskOrder.NoCompletes) }
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center
        ) {
            MyRadioButton(
                text = "Categoria",
                selected = taskOrder is TaskOrder.Category,
                onSelect = { onOrderChange(TaskOrder.Category) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            MyRadioButton(
                text = "Prioridad",
                selected = taskOrder is TaskOrder.Priority,
                onSelect = { onOrderChange(TaskOrder.Priority) }
            )
        }
    }
}

@Composable
fun MyRadioButton(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = { onSelect() },
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.tertiary,
                unselectedColor = MaterialTheme.colorScheme.onBackground
            )
        )
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}
