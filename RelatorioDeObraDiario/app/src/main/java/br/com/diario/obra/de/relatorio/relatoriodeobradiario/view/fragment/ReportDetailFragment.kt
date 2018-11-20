package br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.R
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.databinding.FragmentReportDetailBinding
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.connector.MainActivityConnector
import br.com.diario.obra.de.relatorio.relatoriodeobradiario.view.databinding.ProjectReportDetailDT

class ReportDetailFragment : Fragment() {

    private val mainActivityConnector: MainActivityConnector by lazy {
        context as MainActivityConnector
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_report_detail, container, false
        ) as FragmentReportDetailBinding

        binding.projectReportDetail = ProjectReportDetailDT(
            mainActivityConnector.getCurrentViewProject()!!.name,
            mainActivityConnector.getCurrentViewProject()!!.image,
            mainActivityConnector.getCurrentViewProject()!!.delivery_date,
            mainActivityConnector.getCurrentViewReport()!!.reference_date,
            mainActivityConnector.getCurrentViewReport()!!.creation_date,
            mainActivityConnector.getCurrentViewReport()!!.resume,
            mainActivityConnector.getCurrentViewReport()!!.body_report
        )

        return binding.root
    }


}
