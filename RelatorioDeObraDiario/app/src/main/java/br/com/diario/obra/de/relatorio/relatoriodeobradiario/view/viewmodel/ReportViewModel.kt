package br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.projects.Project
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.reports.Report
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.reports.ReportRepository

class ReportViewModel(application: Application) :
    AndroidViewModel(application){

    private val reportRepository = ReportRepository(application)

    val allReports = reportRepository.allReports

    fun insert(report: Report) {
        reportRepository.insert(report)
    }

    fun delete(report: Report) {
        reportRepository.delete(report)
    }

    fun getAllReportsByProject(project: Long) {
        reportRepository.findAllReportsByProject(project)
    }
}