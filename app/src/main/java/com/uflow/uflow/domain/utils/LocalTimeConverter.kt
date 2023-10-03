package com.uflow.uflow.domain.utils

import androidx.room.TypeConverter
import java.time.LocalTime

class LocalTimeConverter {
    @TypeConverter
    fun fromLocalTime(localTime: LocalTime): String {
        return localTime.toString()
    }

    @TypeConverter
    fun toLocalTime(localTimeStr: String): LocalTime {
        return LocalTime.parse(localTimeStr)
    }

}