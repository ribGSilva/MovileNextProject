package br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.databinding

import android.databinding.BaseObservable
import java.util.*

class ProjectReportDetailDT (
    val projectName: String,
    val projectImage: String,
    val projectDeliveryDate: Calendar,
    val reportReferenceDate: Calendar,
    val reportCreationDate: Calendar,
    val reportResume: String,
    val reportBody: String
) : BaseObservable()