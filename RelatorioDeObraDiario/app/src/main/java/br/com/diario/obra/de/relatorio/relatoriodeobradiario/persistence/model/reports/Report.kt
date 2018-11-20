package br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.reports

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.projects.Project
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity(
    tableName = "report_table",
    foreignKeys = [
        ForeignKey(
            entity = Project::class,
            parentColumns = arrayOf("project_id"),
            childColumns = arrayOf("project_id"),
            onDelete = CASCADE
        )
    ],
    indices = [Index(value = ["project_id", "report_id"], name = "report_index", unique = true)]
)
data class Report(

        @PrimaryKey(autoGenerate = true)
        @NotNull
        @ColumnInfo(name = "report_id")
        val id: Long?,

        @NotNull
        @ColumnInfo(name = "reference_date")
        val reference_date: Calendar,

        @NotNull
        @ColumnInfo(name = "creation_date")
        val creation_date: Calendar,

        @NotNull
        @ColumnInfo(name = "resume")
        val resume: String,

        @NotNull
        @ColumnInfo(name = "body_report")
        val body_report: String,

        @NotNull
        @ColumnInfo(name = "project_id")
        val id_project: Long

    )