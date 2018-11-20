package br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.diario.obra.de.relatorio.relatoriodeobradiario.R
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.projects.Project
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.adapter.ProjectAdapter
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.connector.MainActivityConnector
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.enums.Fragments
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_project.*
import android.support.v7.app.AlertDialog

class ProjectFragment : Fragment() {

    private val mainActivityConnector: MainActivityConnector by lazy {
        context as MainActivityConnector
    }

    private val projectViewModel : ProjectViewModel by lazy {
        ViewModelProviders.of(activity!!).get(ProjectViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ProjectAdapter(context!!, {
            mainActivityConnector.setCurrentViewProject(it)
            mainActivityConnector.changeFragment(Fragments.REPORT_FRAGMENT.name, true)
        }, {
            showDeleteDialog(it)
        })

        project_recycler_view.adapter = adapter
        project_recycler_view.layoutManager = LinearLayoutManager(context!!)

        projectViewModel.allProjects.observe(this,
            Observer { projects ->
                projects?.let { adapter.items = projects }
            })

        project_fab.setOnClickListener {
            mainActivityConnector.changeFragment(Fragments.NEW_PROJECT_FRAGMENT.name, true)
        }
    }

    private fun showDeleteDialog(projectToDelete: Project): Boolean {

        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Sure you wanna remove this project?")

        builder.setNegativeButton(android.R.string.no) {dialog, _ ->
            dialog.dismiss()
        }
        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            projectViewModel.delete(projectToDelete)
            dialog.dismiss()
        }

        builder.create().show()

        return true

    }
}
