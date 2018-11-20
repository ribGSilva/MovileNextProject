package br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.utils

import java.text.SimpleDateFormat
import java.util.*

object Formatter {

    object Date {

        const val DD_MM_YYYY = "dd/MM/yyyy"

        fun formatDate(date: Calendar, pattern: String) =
            SimpleDateFormat(pattern, Locale.getDefault()).format(date.time)
    }
}