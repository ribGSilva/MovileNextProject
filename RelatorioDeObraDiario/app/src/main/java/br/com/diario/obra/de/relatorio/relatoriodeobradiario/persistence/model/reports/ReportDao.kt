package br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.reports

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.*

@Dao
interface ReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(report: Report)

    @Query("DELETE FROM report_table")
    fun deleteAll()

    @Query("SELECT * FROM report_table WHERE project_id = :projectId ORDER BY reference_date DESC")
    fun findAllByProject(projectId: Long): List<Report>

    @Delete
    fun deleteOne(report: Report)
}