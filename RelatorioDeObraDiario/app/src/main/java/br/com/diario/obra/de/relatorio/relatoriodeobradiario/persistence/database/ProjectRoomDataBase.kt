package br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.util.Log
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.projects.Project
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.projects.ProjectDao
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.reports.Report
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.reports.ReportDao
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.utils.RoomConverters
import org.jetbrains.anko.doAsync
import java.util.*

@Database(entities = [Project::class, Report::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class ProjectRoomDataBase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    abstract fun reportDao(): ReportDao

    companion object {
        @Volatile private var instance: ProjectRoomDataBase? = null

        private val DATABASE_NAME = "project_database"

        private val ROOM_LOG = "room_db"

        private val roomDataBaseCallback = object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                instance?.let { roomDb ->
                    doAsync {
                        val projectDao = roomDb.projectDao()

                        projectDao.deleteAll()

                        val project = Project(null,  "Nome do projeto", "https://images.trvl-media.com/hotels/20000000/19470000/19468500/19468499/3913b3ae_z.jpg", Calendar.getInstance() )
                        projectDao.insert(project)

                        val project2 = Project(null,  "Nome do projeto dois", "https://img2.cgtrader.com/items/149076/7792ab6957/large/big-house-3d-model-skp.jpg", Calendar.getInstance() )
                        projectDao.insert(project2)

                        val projects = projectDao.findAllProjects()
                        val reportDao = roomDb.reportDao()

                        reportDao.deleteAll()

                        val report = Report(null, Calendar.getInstance(), Calendar.getInstance(), "resumo",
                                        "body", projects[0].id!!)


                        reportDao.insert(report)

                        val report2 = Report(null, Calendar.getInstance(), Calendar.getInstance(), "resumo2",
                            "body2", projects[1].id!!)


                        reportDao.insert(report2)
                    }

                }

                Log.i(ROOM_LOG, "db successful started")
            }
        }

        fun getInstance(context: Context): ProjectRoomDataBase {
            if (instance == null) {
                synchronized(ProjectRoomDataBase::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProjectRoomDataBase::class.java,
                        DATABASE_NAME
                    ).addCallback(roomDataBaseCallback).build()
                }
            }

            if (instance == null) {
                Log.e(ROOM_LOG, "db NOT started")
            }

            return instance!!
        }
    }
}