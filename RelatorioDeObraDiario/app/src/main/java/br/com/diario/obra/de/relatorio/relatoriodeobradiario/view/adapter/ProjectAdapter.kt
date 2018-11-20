package br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.R
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.projects.Project
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.utils.Formatter.Date
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.project_item.view.*


class ProjectAdapter(
    val context: Context,
    private val clickListener: (Project) -> Unit,
    private val deleteListener: (Project) -> Boolean
) : RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {

    var items: List<Project> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.project_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bindView(item, clickListener, deleteListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(
            item: Project,
            clickListener: (Project) -> Unit,
            deleteListener: (Project) -> Boolean
        ) {

            item.delivery_date?.let { itemView.tv_last_update.text = "Delivery date: ${Date.formatDate(it, Date.DD_MM_YYYY)}" }

            if (!item.image.isBlank())
                Picasso.get().load(item.image).into(itemView.project_item_image_view)

            itemView.tv_project_name.text = item.name
            itemView.setOnClickListener { clickListener(item) }
            itemView.setOnLongClickListener { deleteListener(item) }

        }
    }
}