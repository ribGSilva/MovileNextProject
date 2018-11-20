package br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.R
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.projects.Project
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.reports.Report
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.utils.Formatter.Date
import kotlinx.android.synthetic.main.report_item.view.*

class ReportAdapter(
    val context: Context,
    private val listener: (Report) -> Unit,
    private val deleteListener: (Report) -> Boolean
) : RecyclerView.Adapter<ReportAdapter.ViewHolder>(){

    var items: List<Report> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.report_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bindView(item, listener, deleteListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(
            item: Report,
            listener: (Report) -> Unit,
            deleteListener: (Report) -> Boolean
        ) {

            itemView.tv_reference_date.text = "Report date: ${Date.formatDate(item.reference_date, Date.DD_MM_YYYY)}"

            itemView.tv_report_resume.text = item.resume
            itemView.setOnClickListener { listener(item) }
            itemView.setOnLongClickListener { deleteListener(item) }

        }
    }
}