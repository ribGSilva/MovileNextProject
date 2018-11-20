package br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.projects

import android.app.Application
import android.arch.lifecycle.LiveData
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.database.ProjectRoomDataBase
import org.jetbrains.anko.doAsync

class ProjectRepository(application: Application) {

    private val projectDao : ProjectDao
    val allProjects : LiveData<List<Project>>

    init {
        val db = ProjectRoomDataBase.getInstance(application)
        projectDao = db.projectDao()
        allProjects = projectDao.findAll()
    }

    fun insert(project: Project) {
        doAsync {
            projectDao.insert(project)
        }
    }

    fun delete(project: Project) {
        doAsync {
            projectDao.deleteOne(project)
        }
    }

}