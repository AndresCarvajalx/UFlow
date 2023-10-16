package com.uflow.uflow.ui.presentation.work_tasks.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowCircleDown
import androidx.compose.material.icons.outlined.ArrowCircleUp
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.ModeEditOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uflow.uflow.domain.models.WorkTask
import com.uflow.uflow.domain.models.WorkTask.Companion.iconsOptions
import com.uflow.uflow.ui.theme.cardBackgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkTaskItem(
    modifier: Modifier = Modifier,
    workTask: WorkTask,
    onClickEdit: () -> Unit,
    onClickDelete: () -> Unit,
    onClickWorkTask: () -> Unit,
    showDescription: MutableState<Boolean>,
    onCheckWorkTask: () -> Unit,
) {
    val showAll = remember {
        mutableStateOf(false)
    }
    Card(
        modifier = modifier,
        border = BorderStroke(1.dp, Color.Black),
        elevation = CardDefaults.cardElevation(5.dp),
        onClick = {
            if (showAll.value) {
                onClickWorkTask()
            }
        },
        colors = CardDefaults.cardColors(
            containerColor = if (workTask.isComplete) {
                MaterialTheme.colorScheme.background
            } else {
                cardBackgroundColor
            }
        )
    ) {
        Row() {
            AnimatedVisibility(
                visible = showDescription.value,
            ) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .defaultMinSize(minHeight = 200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = workTask.description, style = MaterialTheme.typography.bodyMedium)
                }
            }


            AnimatedVisibility(
                visible = !showDescription.value,
            ) {
                Row(
                    modifier = Modifier
                        .padding(0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.5f, true)
                            .align(Alignment.CenterVertically),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        if (!showAll.value) {
                            Row(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    modifier = Modifier
                                        .height(40.dp)
                                        .width(40.dp),
                                    imageVector = iconsOptions[workTask.icon].icon?.imageVector!!,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = workTask.priority.toString(),
                                    style = TextStyle(
                                        fontStyle = FontStyle.Italic,
                                        fontSize = 30.sp,
                                        fontFamily = FontFamily.SansSerif,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    textAlign = TextAlign.Center,
                                    maxLines = 1
                                )
                            }
                        }

                        if (showAll.value) {
                            Icon(
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp),
                                imageVector = iconsOptions[workTask.icon].icon?.imageVector!!,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                //modifier = Modifier.fillMaxWidth(),
                                text = workTask.priority.toString(),
                                style = TextStyle(
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily.SansSerif
                                ),
                                textAlign = TextAlign.Center,
                                maxLines = 2
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            //.fillMaxHeight()
                            .weight(2f, true),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = workTask.className,
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        AnimatedVisibility(visible = showAll.value) {
                            Column(
                                //modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = workTask.assignment,
                                    style = MaterialTheme.typography.titleMedium,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = workTask.subject,
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    //    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(text = workTask.toDoDate.toString())
                                    Spacer(modifier = Modifier.width(15.dp))
                                    Text(text = workTask.toDeliveryDate.toString())
                                }
                            }
                        }

                    }

                    Column(
                        //modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(onClick = { showAll.value = !showAll.value }) {
                            Icon(
                                imageVector = if (showAll.value) {
                                    Icons.Outlined.ArrowCircleUp
                                } else {
                                    Icons.Outlined.ArrowCircleDown
                                },
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primaryContainer
                            )
                        }
                        AnimatedVisibility(visible = showAll.value) {
                            Column(
                                //      modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Checkbox(
                                    checked = workTask.isComplete, onCheckedChange = {
                                        onCheckWorkTask()
                                    }, modifier = Modifier.clip(shape = CircleShape)
                                )

                                IconButton(onClick = { onClickEdit() }) {
                                    Icon(
                                        imageVector = Icons.Outlined.ModeEditOutline,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }

                                IconButton(onClick = { onClickDelete() }) {
                                    Icon(
                                        imageVector = Icons.Outlined.DeleteOutline,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}