package br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.databinding

import android.databinding.*
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.utils.Formatter
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.utils.Formatter.Date
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.utils.bindable
import com.android.databinding.library.baseAdapters.BR
import java.util.*

class ProjectDT(
    val id: Long? = null,
    name: String,
    val image: String,
    deliveryDate: Calendar
) : BaseObservable() {
    @get:Bindable
    var name by bindable(name, BR.name)

    @get:Bindable
    var deliveryDate by bindable(deliveryDate, BR.deliveryDate)
}