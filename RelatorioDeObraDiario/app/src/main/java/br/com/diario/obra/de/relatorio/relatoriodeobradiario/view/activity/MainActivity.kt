package br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.activity

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.R
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.projects.Project
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.reports.Report
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.connector.MainActivityConnector
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.enums.Fragments
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.fragment.*
import com.ribeiro.gabriel.easyfragmentmanager.EasyFragmentManager

class MainActivity : FragmentActivity(), MainActivityConnector {

    /* properties */

    private var currentProject: Project? = null

    private var currentReport: Report? = null

    private lateinit var easyFragmentManager: EasyFragmentManager

    private fun getFragmentMap() = mapOf(
        Fragments.PROJECT_FRAGMENT.name to ProjectFragment::class.java,
        Fragments.REPORT_FRAGMENT.name to ReportFragment::class.java,
        Fragments.NEW_PROJECT_FRAGMENT.name to AddProjectFragment::class.java,
        Fragments.NEW_REPORT_FRAGMENT.name to AddReportFragment::class.java,
        Fragments.REPORT_DETAIL_FRAGMENT.name to ReportDetailFragment::class.java
    )

    /* methods */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        easyFragmentManager = EasyFragmentManager.newInstance(
            this, // main activity that extends FragmentActivity
            R.id.content_panel, // place where the fragments will appear
            getFragmentMap(), // the map with all the fragment
            Fragments.PROJECT_FRAGMENT.name, // the first fragment that will pop up
            supportFragmentManager // pass the supportFragmentManager from your activity
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        easyFragmentManager.updateManagerOnBackStackChange() //will keep the manager updated
    }

    override fun changeFragment(newFragmentType: String, addToBackStack: Boolean) {
        easyFragmentManager.changeFragment(newFragmentType, addToBackStack);
    }

    override fun setCurrentViewProject(project: Project) {
        currentProject = project
    }

    override fun getCurrentViewProject() = currentProject

    override fun setCurrentViewReport(report: Report) {
        currentReport = report
    }

    override fun getCurrentViewReport() = currentReport

}
