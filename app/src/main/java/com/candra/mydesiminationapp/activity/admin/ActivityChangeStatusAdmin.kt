package com.candra.mydesiminationapp.activity.admin

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.candra.mydesiminationapp.R
import com.candra.mydesiminationapp.adapter.AdapterMember
import com.candra.mydesiminationapp.data.source.local.toGenerateMember
import com.candra.mydesiminationapp.data.source.remote.network.States
import com.candra.mydesiminationapp.databinding.AcitivityChangeStatusBinding
import com.candra.mydesiminationapp.helper.Constant
import com.candra.mydesiminationapp.helper.Help
import com.candra.mydesiminationapp.helper.TempDataTwo
import com.candra.mydesiminationapp.helper.viewmodel.administrator.ResearchDisseminationAdminViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ActivityChangeStatusAdmin: AppCompatActivity()
{
    private lateinit var binding: AcitivityChangeStatusBinding
    @Inject
    lateinit var localStore: TempDataTwo
    private var valueStatus = 0
    private val adapterMember by lazy { AdapterMember() }
    private val researchDisseminationViewModelAdmin by viewModels<ResearchDisseminationAdminViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AcitivityChangeStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Help.setToolbar(1,supportActionBar,"Ubah Status Desiminasi")
        lifecycleScope.launch {
            localStore.getToken().collect{
                if (it.category == Constant.ADMINISTRATOR) {
                    receivedDataFromShowDisseminationAdmin(it.token)
                }
            }
        }
    }

    private fun receivedDataFromShowDisseminationAdmin(token: String){
        val id = intent.getIntExtra(Constant.PATH_ID,0)
         intent.getStringExtra(Constant.STATUS_ID)?.let { status ->
             binding.apply {
                 when(status){
                     Constant.STATUS_REGISTER_IS_SUBMITTED_PROGRAM_STUDY -> {
                         rbRegisterSubmitted.text = Constant.STATUS_REGISTER_REJECTED_PROGRAM_STUDY
                         rbReject.text = Constant.STATUS_REGISTER_APPROVE_PROGRAM_STUDY
                         btnSave.setOnClickListener {
                            setValidateStatus(token,id,5,6)
                         }
                     }
                     Constant.STATUS_REPORT_SUBMIT_PROGRAM_STUDY ->{
                         rbRegisterSubmitted.text = Constant.STATUS_REPORT_REJECTED_PROGRAM_STUDY
                         rbReject.text = Constant.STATUS_REPORT_APPROVE_PROGRAM_STUDY
                         btnSave.setOnClickListener {
                             setValidateStatus(token,id,11,12)
                         }
                     }
                     Constant.STATUS_REPORT_APPROVED_BY_THE_DEAN -> {
                         rbRegisterSubmitted.text = Constant.ARTICLE_SUBMITTED_TO_PROGRAM_STUDY
                         rbReject.visibility = View.GONE
                         btnSave.setOnClickListener {
                             setValidateStatus(token,id,16)
                         }
                     }
                 }
                 Help.showCardScheduleDissemination(status,cardScheduleDissemination,scheduleDessimination)
                 showAllData(token,id)
             }
         }
    }

    private fun setValidateStatus(token: String,id: Int,valueStatus1: Int,valueStatus2: Int = 0){
        binding.apply {
            val note = tilNote.editText?.text.toString()
            if (rgChoiseStatus.checkedRadioButtonId == -1 || note.isEmpty()){
                Help.showToast(this@ActivityChangeStatusAdmin,"Periksa kembali data anda ada yang kosong")
            }else{
                when(rgChoiseStatus.checkedRadioButtonId){
                    R.id.rb_register_submitted -> valueStatus = valueStatus1
                    R.id.rb_reject -> valueStatus = valueStatus2
                }
                saveDataStatus(token,id,valueStatus,note)
            }
        }
    }

    private fun showAllData(token: String,id: Int)
    {
        with(binding){
            researchDisseminationViewModelAdmin.showResearchDisseminationAdministrator(token,id).observe(this@ActivityChangeStatusAdmin){ dataIndex ->
                when(dataIndex){
                     is States.Loading -> {}
                     is States.Success -> {
                        val dataObserver = dataIndex.data
                        dataObserver.let {
                            valueIdDesimination.text = "#${it.id}"
                            valueStatusDesimination.text = it.statusCaption ?: "?"
                            valueTittleDesimination.text = it.title?: "?"
                            valueAbstract.text = Help.removeHtmlTags(it.abstract?: "?")
                            valueKeyword.text = it.keywords?: "?"
                            valueLecturer.text = it.lecturer.name?: "?"
                            valueCategoryJournal.text = it.journal.journalGradeCaption?: "?"
                            valueNameJournal.text = it.journal.publisherName
                            valueNumberIssn.text = it.journal.publisherNumber.toString()
                            valuePageArticle.text = it.journal.articleUrl
                            valuePagePublisher.text = it.journal.publisherUrl
                            valueYearJournal.text = it.journal.journalYear.toString()
                            valueVolumeJournal.text = it.journal.journalVolume.toString()
                            valueNumberJournal.text = it.journal.journalNumber.toString()
                            valueResearchDesimination.text = it.document.filename
                            valueArticle.text = it.article.filename
                            valueMaterialPresentation.text = it.presentation.filename
                            valueFreeAdmin.text = it.administration.filename
                            adapterMember.submitListData(it.members.toGenerateMember())
                            Help.showCardScheduleDissemination(it.statusCaption,cardScheduleDissemination,scheduleDessimination)
                            Help.visibleDeterminationDecision(it.statusCaption,binding.determinationDecision,binding.valueDeterminationDecision)
                        }
                    }
                    is States.Failed -> {Help.showToast(this@ActivityChangeStatusAdmin,dataIndex.message)}
                    else -> {}
                }
            }
        }
    }

    private fun saveDataStatus(token: String,id: Int,status_id: Int,note: String){
        researchDisseminationViewModelAdmin.changeStatusResearchDisseminationAdministrator(token,id,status_id,note).observe(this@ActivityChangeStatusAdmin)
        {
            when(it){
                is States.Loading -> {}
                is States.Success -> {
                    Help.showToast(this@ActivityChangeStatusAdmin,it.data)
                }
                is States.Failed -> {Help.showToast(this@ActivityChangeStatusAdmin,it.message)}
                else -> {}
            }
        }
    }
}