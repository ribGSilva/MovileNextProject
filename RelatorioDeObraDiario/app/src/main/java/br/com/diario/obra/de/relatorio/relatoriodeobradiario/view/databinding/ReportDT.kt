package br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.databinding

import android.databinding.BaseObservable
import android.databinding.Bindable
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.utils.bindable
import com.android.databinding.library.baseAdapters.BR
import java.util.*

class ReportDT(

    val id: Long? = null,
    referenceDate: Calendar,
    val creationDate: Calendar,
    resume: String,
    body: String,
    val idProject: Long
) : BaseObservable() {
    @get:Bindable
    var referenceDate by bindable(referenceDate, BR.referenceDate)

    @get:Bindable
    var resume by bindable(resume, BR.resume)

    @get:Bindable
    var body by bindable(body, BR.body)

}