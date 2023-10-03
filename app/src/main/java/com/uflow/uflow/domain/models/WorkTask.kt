package com.uflow.uflow.domain.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.filled.Workspaces
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material.icons.outlined.WorkHistory
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeler.sheets.option.models.Option
import java.sql.Date
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar

@Entity(tableName = "work_task")
data class WorkTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val assignment: String,
    val className: String,
    val description: String,
    val subject: String,
    val priority: Int,
    val toDoDate: LocalDate,
    val toDoTime: LocalTime,
    val toDeliveryDate: LocalDate,
    val toDeliveryTime: LocalTime,
    val isComplete: Boolean = false,
    val icon: Int
){
    companion object {
        val iconsOptions = listOf(
            Option(
                IconSource(Icons.Outlined.WorkOutline),
                titleText = "Icono 1"
            ),
            Option(
                IconSource(Icons.Filled.Work),
                titleText = "Icono 2"
            ),
            Option(
                IconSource(Icons.Filled.Workspaces),
                titleText = "Icono 3"
            ),
            Option(
                IconSource(Icons.Filled.WorkspacePremium),
                titleText = "Icono 4"
            ),
            Option(
                IconSource(Icons.Outlined.WorkspacePremium),
                titleText = "Icono 5"
            ),
            Option(
                IconSource(Icons.Filled.Book),
                titleText = "Icono 6"
            )
        )
    }
}
