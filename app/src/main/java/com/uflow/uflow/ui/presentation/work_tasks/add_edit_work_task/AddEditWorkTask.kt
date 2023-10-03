package com.uflow.uflow.ui.presentation.work_tasks.add_edit_work_task

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Class
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.Subject
import androidx.compose.material.icons.outlined.Timelapse
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.maxkeppeler.sheets.option.OptionDialog
import com.maxkeppeler.sheets.option.models.DisplayMode
import com.maxkeppeler.sheets.option.models.OptionConfig
import com.maxkeppeler.sheets.option.models.OptionSelection
import com.uflow.uflow.domain.models.WorkTask.Companion.iconsOptions
import com.uflow.uflow.ui.presentation.tasks.add_edit_task.components.TopBarComponent
import com.uflow.uflow.ui.theme.UFlowTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class AddEditWorkTask : ComponentActivity() {

    private val viewModel: AddEditWorkTaskViewModel by viewModels()
    private var workTaskId: Int? = null
    private lateinit var editOrSave: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workTaskId = intent.extras?.getInt("workTaskId")
        var title = ""
        title = if (workTaskId == null) {
            editOrSave = "Guardar"
            "Agregar"
        } else {
            if (this.workTaskId != null) {

                workTaskId?.let { AddEditWorkTaskEvent.GetWorkTaskToEdit(it) }
                    ?.let { viewModel.onEvent(it) }
                viewModel.id = workTaskId.toString().toInt()
            }
            editOrSave = "Editar"
            "Editar"
        }
        setContent {
            UFlowTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopBarComponent(
                                title = "$title Tarea de Asignatura",
                                onClickIcon = { finish() },
                                onClickClear = {
                                    viewModel.onEvent(AddEditWorkTaskEvent.ClearAllFields)
                                }
                            )
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(top = it.calculateTopPadding())
                                .fillMaxSize()
                        ) {
                            WorkTaskSection(Modifier.padding(20.dp))
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun WorkTaskSection(modifier: Modifier = Modifier) {
        val calendarState = rememberUseCaseState()
        var isToDoDateSelection by remember {
            mutableStateOf(false)
        }
        var isToDoTimeSelection by remember {
            mutableStateOf(false)
        }
        val scope = rememberCoroutineScope()
        CalendarDialog(
            state = calendarState,
            config = CalendarConfig(
                monthSelection = true,
                yearSelection = true,
                boundary = LocalDate.now()..LocalDate.of(LocalDate.now().year + 2, 12, 31)
            ),
            selection = CalendarSelection.Date { date ->
                try {
                    // toDoDate
                    if (isToDoDateSelection) {
                        if (viewModel.toDeliveryDate == date) {
                            Toast.makeText(
                                applicationContext,
                                "El mes de desarrollo tiene que ser diferente al mes de entrega",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            viewModel.onEvent(AddEditWorkTaskEvent.OnToDoDateChange(date))
                        }
                    } else {
                        // toDoDelivery
                        if (viewModel.toDoDate == date) {
                            Toast.makeText(
                                applicationContext,
                                "El mes de entrega tiene que ser diferente al mes de desarrollo",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            viewModel.onEvent(AddEditWorkTaskEvent.OnToDeliveryDateChange(date))
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        )

        Column(
            modifier = modifier.verticalScroll(rememberScrollState()),
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.className,
                onValueChange = { newText ->
                    viewModel.onEvent(AddEditWorkTaskEvent.OnClassNameChange(newText))
                },
                maxLines = 1,
                label = {
                    Text(text = "Clase")
                },
                placeholder = {
                    Text(text = "(ej:Introducción a la Programación)")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Class, contentDescription = null)
                },
                shape = CircleShape,
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.assignment,
                onValueChange = { newText ->
                    viewModel.onEvent(AddEditWorkTaskEvent.OnAssignmentChange(newText))
                },
                maxLines = 2,
                label = {
                    Text(text = "Assignado")
                },
                placeholder = {
                    Text(text = "(ej:Tarea 1: Programación en Kotlin)")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Assignment, contentDescription = null)
                },
                shape = CircleShape,
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.subject,
                onValueChange = { newText ->
                    viewModel.onEvent(AddEditWorkTaskEvent.OnSubjectChange(newText))
                },
                maxLines = 1,
                label = {
                    Text(text = "Materia")
                },
                placeholder = {
                    Text(text = "(ej: Informática)")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Subject, contentDescription = null)
                },
                shape = CircleShape,
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                value = viewModel.description,
                onValueChange = { newText ->
                    viewModel.onEvent(AddEditWorkTaskEvent.OnDescriptionChange(newText))
                },
                maxLines = 5,
                label = {
                    Text(text = "Descripcion")
                },
                placeholder = {
                    Text(text = "Descripcion (ej: Desarrollar un programa en Kotlin que calcule la suma de los números pares del 1 al 100.)")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Description, contentDescription = null)
                },
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.priority.toString(),
                label = { Text(text = "Prioridad")},
                onValueChange = {
                    try {
                        viewModel.onEvent(AddEditWorkTaskEvent.OnPriorityChange(it.toInt()))
                    } catch (e: Exception) {
                        Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                leadingIcon = {
                    IconButton(onClick = {
                        if (viewModel.priority > 1) {
                            viewModel.priority = viewModel.priority - 1
                        }
                    }
                    ) {
                        Icon(imageVector = Icons.Outlined.Remove, contentDescription = null)

                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        if (viewModel.priority < 10) {
                            viewModel.priority = viewModel.priority + 1
                        }
                    }) {
                        Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                    }
                },
                readOnly = true,
                textStyle = TextStyle(textAlign = TextAlign.Center),

            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    value = viewModel.toDoDate.toString(),
                    onValueChange = {},
                    label = {
                        Text(text = "Fecha de desarrollo")
                    },
                    placeholder = {
                        Text(text = "Lo voy a hacer el dia...")
                    },
                    leadingIcon = {
                        IconButton(onClick = {
                            isToDoDateSelection = true
                            calendarState.show()
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.EditCalendar,
                                contentDescription = null
                            )
                        }
                    },
                    readOnly = true,
                    textStyle = TextStyle(textAlign = TextAlign.Center),
                )
                Spacer(modifier = Modifier.width(5.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    value = viewModel.toDeliveryDate.toString(),
                    onValueChange = {},
                    label = {
                        Text(text = "Fecha de entrega")
                    },
                    placeholder = {
                        Text(text = "Se entrega el dia...")
                    },
                    leadingIcon = {
                        IconButton(onClick = {
                            isToDoDateSelection = false
                            calendarState.show()
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.EventAvailable,
                                contentDescription = null
                            )
                        }
                    },
                    readOnly = true,
                    textStyle = TextStyle(textAlign = TextAlign.Center),
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            val timePickerState = rememberUseCaseState()
            ClockDialog(
                state = timePickerState,
                selection = ClockSelection.HoursMinutes { hours, minutes ->
                    if(isToDoTimeSelection){
                        viewModel.toDoTime = viewModel.toDoTime.withHour(hours).withMinute(minutes).withSecond(0).withNano(0)
                    }else {
                        viewModel.toDeliveryTime = viewModel.toDeliveryTime.withHour(hours).withMinute(minutes).withSecond(0).withNano(0)
                    }
                }
            )

            // PART TO SELECT TIME CLOCK
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    value = viewModel.toDoTime.format(DateTimeFormatter.ofPattern("HH:mm")).toString(),
                    onValueChange = {},
                    label = {
                        Text(text = "Hora de desarrollo")
                    },
                    placeholder = {
                        Text(text = "Lo voy a hacer el dia...")
                    },
                    leadingIcon = {
                        IconButton(onClick = {
                            isToDoTimeSelection = true
                            timePickerState.show()
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Timelapse,
                                contentDescription = null
                            )
                        }
                    },
                    readOnly = true,
                    textStyle = TextStyle(textAlign = TextAlign.Center),

                )
                Spacer(modifier = Modifier.width(5.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    value = viewModel.toDeliveryTime.format(DateTimeFormatter.ofPattern("HH:mm")).toString(),
                    onValueChange = {},
                    label = {
                        Text(text = "Hora de entrega")
                    },
                    leadingIcon = {
                        IconButton(onClick = {
                            isToDoTimeSelection = false
                            timePickerState.show()
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Alarm,
                                contentDescription = null
                            )
                        }
                    },
                    readOnly = true,
                    textStyle = TextStyle(textAlign = TextAlign.Center),

                )
            }


            Spacer(modifier = Modifier.height(20.dp))
            val iconPickerState = rememberUseCaseState()
            OptionDialog(
                state = iconPickerState,
                selection = OptionSelection.Single(
                    options = iconsOptions,
                ) { index, option ->
                    viewModel.onEvent(AddEditWorkTaskEvent.OnIconChange(index))
                },
                config = OptionConfig(
                    mode = DisplayMode.GRID_VERTICAL,
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp),
                        imageVector = iconsOptions[viewModel.icon].icon?.imageVector!!,
                        contentDescription = null
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { iconPickerState.show() }
                    ) {
                        Text(text = "Click para selecionar icono")
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = viewModel.className.isNotBlank() && viewModel.assignment.isNotBlank() && viewModel.subject.isNotBlank(),
                onClick = {
                    scope.launch {
                        viewModel.onEvent(AddEditWorkTaskEvent.SaveWorkTask(applicationContext))
                        Toast.makeText(
                            applicationContext,
                            "Agregado Exitosamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    finish()
                },
            ) {
                Icon(imageVector = Icons.Outlined.WorkOutline, contentDescription = null)
                Text(text = "$editOrSave workTask")
            }
        }
    }
}