package br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.projects.Project
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.projects.ProjectRepository

class ProjectViewModel(application: Application) :
        AndroidViewModel(application){

    private val projectRepository = ProjectRepository(application)

    val allProjects =  projectRepository.allProjects

    fun insert(project: Project) {
        projectRepository.insert(project)
    }

    fun delete(project: Project) {
        projectRepository.delete(project)
    }
}