@file:Suppress("DEPRECATION")
package com.candra.mydesiminationapp.activity.admin
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.candra.mydesiminationapp.activity.ChangeStatusStudentActivity
import com.candra.mydesiminationapp.activity.LogActivity
import com.candra.mydesiminationapp.adapter.AdapterMember
import com.candra.mydesiminationapp.data.source.local.LocalIndexAdministratorItem
import com.candra.mydesiminationapp.data.source.remote.network.States
import com.candra.mydesiminationapp.databinding.ActivityShowDisseminationAdminBinding
import com.candra.mydesiminationapp.helper.Constant
import com.candra.mydesiminationapp.helper.Help
import com.candra.mydesiminationapp.helper.TempDataTwo
import com.candra.mydesiminationapp.helper.viewmodel.Lecturer.ResearchDisseminationViewModel
import com.candra.mydesiminationapp.helper.viewmodel.administrator.ResearchDisseminationAdminViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ShowDisseminationActivity: AppCompatActivity()
{
    private lateinit var binding: ActivityShowDisseminationAdminBinding
    private val showDisseminationViewModel by viewModels<ResearchDisseminationAdminViewModel>()
    private val disseminationViewModel by viewModels<ResearchDisseminationViewModel>()
    private val adapterMember by lazy { AdapterMember() }
    @Inject
    lateinit var localStore: TempDataTwo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowDisseminationAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerViewMember()
        Help.setToolbar(1,supportActionBar,"Disseminasi Penelitian")
        lifecycleScope.launch {
            localStore.getToken().collect{tokenAdministrator ->
                if (tokenAdministrator.category == Constant.ADMINISTRATOR){
                    setAllData(tokenAdministrator.token)
                }
            }
        }
    }

    private fun setAllData(token: String){
        intent.getParcelableExtra<LocalIndexAdministratorItem>(Constant.EXTRA_STUDENT_ID)?.let {
            with(binding){
                showDisseminationViewModel.showResearchDisseminationAdministrator(token,it.id).observe(this@ShowDisseminationActivity)
                { data ->
                    when(data){
                        is States.Loading -> {}
                        is States.Success -> {
                            valueIdDesimination.text = "#${data.data.id}"
                            valueStatusDesimination.text = data.data.statusCaption
                            valueTittleDesimination.text = data.data.title
                            valueAbstract.text = Help.removeHtmlTags(data.data.abstract)
                            valueKeyword.text = data.data.keywords
                            valueLecturer.text = data.data.lecturer.name
                            valueNameJournal.text = data.data.journal.publisherName
                            valueYearJournal.text = data.data.journal.journalYear.toString()
                            valueCategoryJournal.text = data.data.journal.journalGradeCaption
                            valueNumberIssn.text = data.data.journal.publisherNumber
                            valuePagePublisher.text = data.data.journal.publisherUrl
                            valuePageArticle.text = data.data.journal.articleUrl
                            valueVolumeJournal.text = data.data.journal.journalVolume.toString()
                            valueNumberJournal.text = data.data.journal.journalNumber.toString()
                            valueResearchDesimination.text = data.data.document.filename
                            valueMaterialPresentation.text = data.data.presentation.filename
                            valueFreeAdmin.text = data.data.administration.filename
                            valueArticle.text = data.data.article.filename
                            valueRoomDissemination.text = data.data.schedule.classRoomId
                            valueStartDissemination.text = data.data.schedule.startTime
                            valueEndDissemination.text = data.data.schedule.endTime
                            valueQuotaParticipants.text = data.data.schedule.quota
                            Help.visibleDeterminationDecision(status = data.data.statusCaption,
                                determinationDecision,valueDeterminationDecision)
                            val dataId = valueIdDesimination.text.toString()
                            val textId = dataId.substring(1)
                            val numberId = textId.toInt()
                            disseminationViewModel.getMember(numberId,token).observe(this@ShowDisseminationActivity) { dataMember ->
                                when (dataMember) {
                                    is States.Loading -> {}
                                    is States.Success -> {
                                        adapterMember.submitListData(dataMember.data)
                                    }

                                    is States.Failed -> {
                                        Help.showToast(
                                            this@ShowDisseminationActivity,
                                            dataMember.message
                                        )
                                    }

                                    else -> {}
                                }
                            }
                            btnChangeStatus.setOnClickListener {
                                actionChangeStatusAdmin(data.data.statusCaption,data.data.id)
                            }
                            btnCorrection.setOnClickListener {
                                actionCorrectionAdmin(data.data.id,token)
                            }
                        }
                        is States.Failed -> {Help.showToast(this@ShowDisseminationActivity,"Terjadi kesalahan pada server")}
                        else -> {}
                    }

                }
                btnBack.setOnClickListener {
                    startActivity(Intent(this@ShowDisseminationActivity,IndexAdministratorActivity::class.java)).also { finish() }
                }
                btnNote.setOnClickListener {
                    sendDataIdToLogActivity(it.id,token)
                }
            }
        }
    }

    private fun actionCorrectionAdmin(id: Int,token: String){
        Intent(this@ShowDisseminationActivity,ActivityChangeStatusAdmin::class.java).apply {
            putExtra(Constant.EXTRA_STUDENT_ID,id)
            putExtra(Constant.EXTRA_TOKEN,token)
        }.also { startActivity(it) }
    }

    private fun actionChangeStatusAdmin(statusCaption: String,id: Int){
        Intent(this@ShowDisseminationActivity,ActivityChangeStatusAdmin::class.java).apply {
            putExtra(Constant.PATH_ID,id)
            putExtra(Constant.STATUS_ID,statusCaption)
        }.also { startActivity(it) }
    }

    private fun sendDataIdToLogActivity(idStudent: Int,token: String){
        Intent(this@ShowDisseminationActivity,LogActivity::class.java).apply {
            putExtra(Constant.POSITON_ID,idStudent)
            putExtra(Constant.EXTRA_TOKEN_ADMIN,token)
        }.also { startActivity(it) }
    }

    private fun recyclerViewMember(){
        binding.rvTeamResearch.apply {
            layoutManager = if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                GridLayoutManager(this@ShowDisseminationActivity,2)
            else LinearLayoutManager(this@ShowDisseminationActivity)
            setHasFixedSize(true)
            adapter = adapterMember
        }
    }

}