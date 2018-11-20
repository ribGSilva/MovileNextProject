package br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.databinding

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.utils.Formatter
import com.squareup.picasso.Picasso
import java.util.*

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("text")
    fun convertCalendar(textView: TextView, calendar: Calendar?) {
        calendar?.let {
            textView.text = Formatter.Date.formatDate(it, Formatter.Date.DD_MM_YYYY)
        }
    }

    @JvmStatic
    @BindingAdapter("image")
    fun loadImage(imageView: ImageView, imageUrl: String?) {
        if (imageUrl != null && imageUrl.isNotBlank())
            Picasso.get().load(imageUrl).into(imageView)
    }
}