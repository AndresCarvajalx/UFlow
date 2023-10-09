package com.uflow.uflow.ui.presentation.work_tasks

import android.Manifest
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.uflow.uflow.domain.utils.NotificationUtil
import com.uflow.uflow.ui.presentation.work_tasks.add_edit_work_task.AddEditWorkTask
import com.uflow.uflow.ui.presentation.work_tasks.components.DialogDeleteAllWorkTask
import com.uflow.uflow.ui.presentation.work_tasks.components.SearchSection
import com.uflow.uflow.ui.presentation.work_tasks.components.WorkTaskItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeworkScreen(
    navController: NavController,
    viewModel: WorkTaskViewModel = hiltViewModel()
) {
    val text = remember { mutableStateOf(TextFieldValue("")) }
    val isDialogOpen = remember { mutableStateOf(false) }

    if (isDialogOpen.value) {
        DialogDeleteAllWorkTask(isOpen = isDialogOpen) {
            viewModel.onEvent(WorkTaskEvent.OnDeleteAllWorkTask)
            isDialogOpen.value = false
        }
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    NotificationUtil(context)
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {},
                actions = {
                    SearchSection(
                        state = text,
                        onSearch = {
                            viewModel.onEvent(WorkTaskEvent.OnSearch(it))
                        }
                    )
                    IconButton(onClick = {
                        isDialogOpen.value = true
                    }) {
                        Icon(imageVector = Icons.Default.DeleteSweep, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .padding(top = it.calculateTopPadding())
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                items(viewModel.state.value.workTasks) { workTask ->
                    val showDescription = remember {
                        mutableStateOf(
                            false
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    WorkTaskItem(
                        modifier = Modifier.fillMaxWidth(),
                        workTask = workTask,
                        onClickEdit = {
                            context.startActivity(
                                Intent(
                                    context,
                                    AddEditWorkTask::class.java
                                ).putExtra("workTaskId", workTask.id)
                            )
                        },
                        onClickDelete = {
                            scope.launch {
                                viewModel.onEvent(WorkTaskEvent.OnDeleteWorkTask(workTask))
                            }
                        },
                        onClickWorkTask = {
                            showDescription.value = !showDescription.value
                        },
                        onCheckWorkTask = {
                            viewModel.onEvent(WorkTaskEvent.OnCheck(workTask))
                        },
                        showDescription = showDescription
                    )
                }
            }
        }
    }
}