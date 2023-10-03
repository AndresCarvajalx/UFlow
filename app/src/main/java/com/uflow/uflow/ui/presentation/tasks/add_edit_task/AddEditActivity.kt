package com.uflow.uflow.ui.presentation.tasks.add_edit_task


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.uflow.uflow.ui.presentation.tasks.add_edit_task.components.TopBarComponent
import com.uflow.uflow.ui.theme.UFlowTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class AddEditActivity : ComponentActivity() {

    private val viewModel: AddEditTaskViewModel by viewModels()
    private var taskId: Int? = null
    private lateinit var editOrSave: String

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskId = intent.extras?.getInt("taskId")
        var title = ""
        title = if (taskId == null) {
            editOrSave = "Guardar"
            "Agregar"
        } else {
            if (this.taskId != null) {
                taskId?.let { AddEditEvent.GetTaskToEdit(it) }?.let { viewModel.onEvent(it) }
                viewModel.id = taskId.toString().toInt()
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
                                title = "$title Todo",
                                onClickIcon = { finish() },
                                onClickClear = {
                                    viewModel.onEvent(AddEditEvent.ClearAllField)
                                }
                            )
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(top = it.calculateTopPadding())
                                .fillMaxSize()
                        ) {
                            AddEditTaskContainer(Modifier.padding(20.dp))
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddEditTaskContainer(
        modifier: Modifier = Modifier
    ) {
        val scope = rememberCoroutineScope()

        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.title,
                onValueChange = { newText ->
                    viewModel.onEvent(AddEditEvent.OnTitleChange(newText))
                },
                singleLine = true,
                maxLines = 1,
                label = {
                    Text(text = "Title")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Title, contentDescription = null)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.description,
                onValueChange = { newText ->
                    viewModel.onEvent(AddEditEvent.OnDescriptionChange(newText))
                },
                maxLines = 5,
                label = {
                    Text(text = "Nota/Descripcion")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Notes, contentDescription = null)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.category,
                onValueChange = { newText ->
                    viewModel.onEvent(AddEditEvent.OnCategoryChange(newText))
                },
                singleLine = true,
                maxLines = 1,
                label = {
                    Text(text = "Categoria")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Category, contentDescription = null)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.priority.toString(),
                onValueChange = { newText ->
                    if (newText.isNotBlank()) {
                        try {
                            if(newText.toInt() > 0){
                                viewModel.onEvent(AddEditEvent.OnPriorityChange(newText.toInt()))
                            }
                            if (viewModel.priority > 10) {
                                Toast.makeText(
                                    applicationContext,
                                    "El maximo es 10",
                                    Toast.LENGTH_SHORT
                                ).show()
                                viewModel.onEvent(AddEditEvent.OnPriorityChange(10))
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "Solo se pueden numeros enteros",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                maxLines = 1,
                label = {
                    Text(text = "Prioridad, Default 1, max 10")
                },
                leadingIcon = {
                    IconButton(onClick = {
                        if(viewModel.priority > 1){
                            viewModel.priority = viewModel.priority - 1
                        }
                    }
                    ) {
                        Icon(imageVector = Icons.Outlined.Remove, contentDescription = null)

                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        if(viewModel.priority < 10){
                            viewModel.priority = viewModel.priority + 1
                        }
                    }) {
                        Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                    }
                },
                textStyle = TextStyle(textAlign = TextAlign.Center)

            )
            Spacer(modifier = Modifier.height(20.dp))
            // ON CLICK BUTTON
            ButtonSaveTask(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                isEnabled = viewModel.title.isNotBlank() && viewModel.priority <= 10 && viewModel.priority > 0
            ) {
                scope.launch {
                    viewModel.onEvent(AddEditEvent.SaveTask)
                    Toast.makeText(
                        applicationContext,
                        "Agregado Exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                finish()
            }
        }
    }

    @Composable
    fun ButtonSaveTask(
        isEnabled: Boolean,
        modifier: Modifier = Modifier,
        onClick: () -> Unit,
    ) {
        OutlinedButton(
            modifier = modifier,
            enabled = isEnabled,
            onClick = { onClick() }
        ) {
            Icon(imageVector = Icons.Outlined.Save, contentDescription = null)
            Text(text = "$editOrSave Todo")
        }
    }
}