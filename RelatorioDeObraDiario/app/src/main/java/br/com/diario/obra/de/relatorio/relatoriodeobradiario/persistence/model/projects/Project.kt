package br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.projects

import android.arch.persistence.room.*
import android.graphics.Bitmap
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity(tableName = "project_table",
    indices = [Index(value = ["project_id"], name = "project_index", unique = true)])
data class Project(

    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "project_id")
    val id: Long? = null,

    @NotNull
    @ColumnInfo(name = "project_name")
    val name : String,

    @ColumnInfo(name = "priject_url_image")
    val image : String,

    @ColumnInfo(name = "project_delivery_date")
    val delivery_date : Calendar

)