package br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.utils

import android.arch.persistence.room.TypeConverter
import java.util.*

object RoomConverters {

    @TypeConverter
    @JvmStatic
    fun fromCalendar(value: Calendar): Long {
        return value.timeInMillis
    }

    @TypeConverter
    @JvmStatic
    fun toCalendar(value: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = value
        return calendar
    }
}
