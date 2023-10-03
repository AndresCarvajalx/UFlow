package com.uflow.uflow.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val priority: Int = 1,
    val category: String = "",
    val isComplete: Boolean = false,
    val date: Long,
)