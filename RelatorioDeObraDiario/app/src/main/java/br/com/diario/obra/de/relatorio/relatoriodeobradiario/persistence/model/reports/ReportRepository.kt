package br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.reports

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.database.ProjectRoomDataBase
import org.jetbrains.anko.doAsync

class ReportRepository(application: Application) {

    private val reportDao: ReportDao
    val allReports : MutableLiveData<List<Report>>

    init {
        val db = ProjectRoomDataBase.getInstance(application)
        reportDao = db.reportDao()
        allReports = MutableLiveData()
    }

    fun insert(report: Report) {
        doAsync {
            reportDao.insert(report)
        }
    }

    fun delete(report: Report) {
        doAsync {
            reportDao.deleteOne(report)
        }
    }

    fun findAllReportsByProject(project: Long) {
        doAsync {
            allReports.postValue(reportDao.findAllByProject(project))
        }
    }
}