package com.uflow.uflow.ui.presentation.work_tasks.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun SearchSection(
    state: MutableState<TextFieldValue>,
    onSearch: (toSearchText: String) -> Unit,
) {
    val showSearchBar = remember {
        mutableStateOf(false)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { showSearchBar.value = !showSearchBar.value }) {
            Icon(
                imageVector = if (!showSearchBar.value) {
                    Icons.Default.Search
                } else {
                    Icons.Default.Close
                },
                contentDescription = null
            )
        }
        AnimatedVisibility(
            visible = showSearchBar.value,
        ) {
            TextField(
                placeholder = { Text(text = "Buscar Tarea", color = Color.Gray) },
                value = state.value,
                onValueChange = { value ->
                    state.value = value
                    onSearch(value.text)
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                /*
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(15.dp)
                    )
                },

                 */
                trailingIcon = {
                    if (state.value != TextFieldValue("")) {
                        IconButton(
                            onClick = {
                                state.value = TextFieldValue("")
                                onSearch(state.value.text)
                            }
                        ) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(15.dp)
                                    .size(48.dp)
                            )
                        }
                    }
                },
                singleLine = true,
                shape = CircleShape,
                colors = TextFieldDefaults.colors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch(state.value.text)
                        showSearchBar.value = false
                    },
                )
            )
        }
    }
}