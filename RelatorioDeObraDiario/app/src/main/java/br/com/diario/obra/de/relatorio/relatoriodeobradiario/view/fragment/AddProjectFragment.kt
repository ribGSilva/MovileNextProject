package br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.diario.obra.de.relatorio.relatoriodeobradiario.R
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.connector.MainActivityConnector
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.viewmodel.ProjectViewModel
import android.databinding.DataBindingUtil
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.databinding.FragmentAddProjectBinding
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.databinding.ProjectDT
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.utils.textChangeObservable
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_add_project.*
import java.util.*
import android.app.DatePickerDialog
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.BR
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.projects.Project
import io.reactivex.rxkotlin.Flowables
import kotlinx.android.synthetic.main.activity_main.view.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.design.longSnackbar

class AddProjectFragment : Fragment() {

    private val projectViewModel : ProjectViewModel by lazy {
        ViewModelProviders.of(activity!!).get(ProjectViewModel::class.java)
    }

    private val nameChangeObservable : Flowable<CharSequence> by textChangeObservable(R.id.et_new_project_name)
    private val deliveryDateChangeObservable : Flowable<CharSequence> by textChangeObservable(R.id.tv_tv_new_project_delivery)

    private lateinit var binding: FragmentAddProjectBinding

    private lateinit var disposable: Disposable

    private val newProject = ProjectDT(null, "", "", Calendar.getInstance())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_project, container, false
        ) as FragmentAddProjectBinding

        binding.project = newProject

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_tv_new_project_delivery.setOnClickListener {
            showDatePicker()
        }

        disposable = Flowables.combineLatest(
            nameChangeObservable,
            deliveryDateChangeObservable
        ) { newName: CharSequence,
            _ ->

            btn_create_project.isEnabled = false

            val nameValid = newName.length > 4

            if (!nameValid) {
                et_new_project_name.error = "Invalid name"
            }

            val yearValid = newProject.deliveryDate.after(Calendar.getInstance())

            if (!yearValid) {
                tv_tv_new_project_delivery.error = "Date cannot be before actual date"
            }


            nameValid && yearValid
        }.subscribe { formValid ->
            btn_create_project.isEnabled = formValid
        }

        btn_create_project.setOnClickListener {
            projectViewModel.insert(Project(
                newProject.id,
                newProject.name,
                newProject.image,
                newProject.deliveryDate
            ))

            longSnackbar(activity!!.contentView!!.rootView.coordinator, "Project Created")

            activity!!.onBackPressed()
        }
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(context!!,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                newProject.deliveryDate.set(year, monthOfYear, dayOfMonth, 0, 0)
                newProject.notifyPropertyChanged(BR.deliveryDate)
            },
            newProject.deliveryDate.get(Calendar.YEAR),
            newProject.deliveryDate.get(Calendar.MONTH),
            newProject.deliveryDate.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

}
