package br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.projects

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projet: Project)

    @Query("DELETE FROM project_table")
    fun deleteAll()

    @Query("SELECT * FROM project_table ORDER BY project_delivery_date DESC")
    fun findAll(): LiveData<List<Project>>

    @Query("SELECT * FROM project_table ORDER BY project_delivery_date DESC")
    fun findAllProjects(): List<Project>

    @Delete
    fun deleteOne(project: Project)
}