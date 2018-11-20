package br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.R
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.projects.Project
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.reports.Report
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.adapter.ReportAdapter
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.connector.MainActivityConnector
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.enums.Fragments
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.viewmodel.ReportViewModel
import kotlinx.android.synthetic.main.fragment_report.*

class ReportFragment : Fragment() {

    private val mainActivityConnector: MainActivityConnector by lazy {
        context as MainActivityConnector
    }

    private val reportViewModel : ReportViewModel by lazy {
        ViewModelProviders.of(activity!!).get(ReportViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ReportAdapter(context!!, {
            mainActivityConnector.setCurrentViewReport(it)
            mainActivityConnector.changeFragment(Fragments.REPORT_DETAIL_FRAGMENT.name, true)
        }, {
            showDeleteDialog(it)
        } )

        report_recycler_view.adapter = adapter
        report_recycler_view.layoutManager = LinearLayoutManager(context!!)

        reportViewModel.getAllReportsByProject(mainActivityConnector.getCurrentViewProject()?.id!!)

        reportViewModel.allReports.observe(this,
            Observer { reports ->
                reports?.let { adapter.items = reports }
            })

        report_fab.setOnClickListener {
            mainActivityConnector.changeFragment(Fragments.NEW_REPORT_FRAGMENT.name, true)
        }
    }

    private fun showDeleteDialog(reportToDelete: Report): Boolean {

        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Sure you wanna remove this report?")

        builder.setNegativeButton(android.R.string.no) {dialog, _ ->
            dialog.dismiss()
        }
        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            reportViewModel.delete(reportToDelete)
            dialog.dismiss()
        }

        builder.create().show()

        return true

    }
}
