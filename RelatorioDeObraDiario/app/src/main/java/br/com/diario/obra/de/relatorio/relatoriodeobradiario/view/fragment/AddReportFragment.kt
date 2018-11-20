package br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.fragment

import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.BR
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.R
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.databinding.FragmentAddReportBinding
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.persistence.model.reports.Report
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.connector.MainActivityConnector
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.databinding.ReportDT
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.utils.textChangeObservable
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.viewmodel.ReportViewModel
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Flowables
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_add_project.*
import kotlinx.android.synthetic.main.fragment_add_report.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.design.longSnackbar
import java.util.*

class AddReportFragment : Fragment() {

    private val mainActivityConnector: MainActivityConnector by lazy {
        context as MainActivityConnector
    }

    private val reportViewModel: ReportViewModel by lazy {
        ViewModelProviders.of(activity!!).get(ReportViewModel::class.java)
    }

    private val resumeChangeObservable: Flowable<CharSequence> by textChangeObservable(R.id.et_new_report_resume)
    private val dateReferenceChangeObservable: Flowable<CharSequence> by textChangeObservable(R.id.tv_new_report_reference)
    private val bodyChangeObservable: Flowable<CharSequence> by textChangeObservable(R.id.et_new_report_body)

    private lateinit var binding: FragmentAddReportBinding

    private lateinit var disposable: Disposable

    private val newReport = ReportDT(
        null,
        Calendar.getInstance(),
        Calendar.getInstance(),
        "",
        "",
        0
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_report, container, false
        ) as FragmentAddReportBinding

        binding.report = newReport
        binding.project = mainActivityConnector.getCurrentViewProject()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_new_report_reference.setOnClickListener {
            showDatePicker()
        }

        disposable = Flowables.combineLatest(
            resumeChangeObservable,
            dateReferenceChangeObservable,
            bodyChangeObservable
        ) { newResume: CharSequence,
            _,
            newBody: CharSequence ->

            btn_create_report.isEnabled = false

            val resumeValid = newResume.length >= 8

            if (!resumeValid) {
                et_new_report_resume.error = "Invalid resume, too short, at least 8 chars"
            }

            val dateReferenceValid = newReport.referenceDate.after(Calendar.getInstance())

            if (!dateReferenceValid) {
                tv_new_report_reference.error = "Date cannot be before actual date"
            }

            val bodyValid = newBody.length >= 20

            if (!bodyValid) {
                et_new_report_body.error = "Invalid body, too short, at least 20 chars"
            }

            resumeValid && dateReferenceValid && bodyValid
        }.subscribe { formValid ->
            btn_create_report.isEnabled = formValid
        }

        btn_create_report.setOnClickListener {
            reportViewModel.insert(
                Report(
                    newReport.id,
                    Calendar.getInstance(),
                    newReport.referenceDate,
                    newReport.resume,
                    newReport.body,
                    mainActivityConnector.getCurrentViewProject()!!.id!!
                )
            )

            longSnackbar(activity!!.contentView!!.rootView.coordinator, "Report Created")

            activity!!.onBackPressed()
        }
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            context!!,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                newReport.referenceDate.set(year, monthOfYear, dayOfMonth, 0, 0)
                newReport.notifyPropertyChanged(BR.referenceDate)
            },
            newReport.referenceDate.get(Calendar.YEAR),
            newReport.referenceDate.get(Calendar.MONTH),
            newReport.referenceDate.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

}
