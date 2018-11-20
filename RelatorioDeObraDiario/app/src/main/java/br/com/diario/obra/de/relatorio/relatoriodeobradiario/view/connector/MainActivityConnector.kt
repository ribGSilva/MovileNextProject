package br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.connector

import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.projects.Project
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.reports.Report
import com.ribeiro.gabriel.easyfragmentmanager.EasyFragmentController

interface MainActivityConnector : EasyFragmentController {

    fun setCurrentViewProject(project: Project)

    fun getCurrentViewProject() : Project?

    fun setCurrentViewReport(report : Report)

    fun getCurrentViewReport() : Report?

}