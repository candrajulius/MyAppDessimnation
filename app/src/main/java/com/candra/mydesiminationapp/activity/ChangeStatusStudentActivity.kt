package com.candra.mydesiminationapp.activity

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.candra.mydesiminationapp.adapter.AdapterMember
import com.candra.mydesiminationapp.data.source.local.toGenerateMemberStudent
import com.candra.mydesiminationapp.data.source.remote.network.States
import com.candra.mydesiminationapp.databinding.AcitivityChangeStatusBinding
import com.candra.mydesiminationapp.helper.Constant
import com.candra.mydesiminationapp.helper.Help
import com.candra.mydesiminationapp.helper.TempDataTwo
import com.candra.mydesiminationapp.helper.viewmodel.student.ResearchDisseminationViewModelStudent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ChangeStatusStudentActivity: AppCompatActivity(){
    private lateinit var binding: AcitivityChangeStatusBinding
    private val researchDisseminationViewModelStudent by viewModels<ResearchDisseminationViewModelStudent>()
    @Inject
    lateinit var localStore: TempDataTwo
    private val adapterMember by lazy { AdapterMember() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AcitivityChangeStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Help.setToolbar(1,supportActionBar,"Ubah Status")
        binding.apply {
            lifecycleScope.launch {
                localStore.getToken().collect{ getToken ->
                    if (getToken.category == Constant.STUDENT){
                        btnSave.setOnClickListener {
                            validateData(getToken.token)
                        }
                        setDetailAllData(getToken.token)
                    }
                }
            }
            btnBack.setOnClickListener {
                callBack.handleOnBackPressed()
            }
            rbReject.visibility = View.GONE
            rbRegisterSubmitted.text = Constant.STATUS_REGISTER_IS_SUBMITTED_SUPERVISING_LECTURER
        }
        setRecyclerView()
    }

    private fun setDetailAllData(token: String){
        with(binding){
            researchDisseminationViewModelStudent.indexStudent(token).observe(this@ChangeStatusStudentActivity){ dataStudent ->
                when(dataStudent){
                    is States.Loading ->{}
                    is States.Success -> {
                        val observerData = dataStudent.data
                        valueIdDesimination.text = "#${observerData.id}"
                        valueStatusDesimination.text = observerData.statusCaption ?: "?"
                        valueTittleDesimination.text = observerData.title?: "?"
                        valueAbstract.text = Help.removeHtmlTags(observerData.abstract?: "?")
                        valueKeyword.text = observerData.keywords?: "?"
                        valueLecturer.text = observerData.lecturer.name?: "?"
                        valueCategoryJournal.text = observerData.journal.journalGradeCaption?: "?"
                        valueNameJournal.text = observerData.journal.publisherName
                        valueNumberIssn.text = observerData.journal.publisherNumber.toString()
                        valuePageArticle.text = observerData.journal.articleUrl
                        valuePagePublisher.text = observerData.journal.publisherUrl
                        valueYearJournal.text = observerData.journal.journalYear.toString()
                        valueVolumeJournal.text = observerData.journal.journalVolume.toString()
                        valueNumberJournal.text = observerData.journal.journalNumber.toString()
                        valueResearchDesimination.text = observerData.document.filename
                        valueArticle.text = observerData.article.filename
                        valueMaterialPresentation.text = observerData.presentation.filename
                        valueFreeAdmin.text = observerData.administration.filename
                        adapterMember.submitListData(observerData.members.toGenerateMemberStudent())
                        Help.showCardScheduleDissemination(observerData.statusCaption,cardScheduleDissemination,binding.scheduleDessimination)
                        Help.visibleDeterminationDecision(observerData.statusCaption,binding.determinationDecision,binding.valueDeterminationDecision)
                    }
                    is States.Failed -> {Help.showToast(this@ChangeStatusStudentActivity,dataStudent.message)}
                    else -> {}
                }
            }
        }
    }



    private fun setRecyclerView(){
        binding.rvTeamResearch.apply {
            layoutManager = if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            ) GridLayoutManager(this@ChangeStatusStudentActivity,2)
            else LinearLayoutManager(this@ChangeStatusStudentActivity)
            setHasFixedSize(true)
            adapter = adapterMember
        }
    }

    private fun validateData(token: String){
        binding.apply {
            val note = tilNote.editText?.text.toString().trim().lowercase()
            val selectedGroup = rgChoiseStatus.checkedRadioButtonId
            if (note.isEmpty()) Help.showToast(this@ChangeStatusStudentActivity,"Catatan anda masih kosong")
            else if (selectedGroup == -1) Help.showToast(this@ChangeStatusStudentActivity,"Harap memilih pilihan")
            else {
                val idDissemination = intent.getIntExtra(Constant.EXTRA_CHANGE_STATUS_MEMBER,0)
                Log.d("TAG", "validateData: Dissemination => $idDissemination Note => $note Token => $token")
//                setAllData(idDissemination,note,token)
            }
        }
    }

    private fun setAllData(id: Int,note: String,token: String){
    researchDisseminationViewModelStudent.changeStatusStudent(token,id,2,note)
        .observe(this@ChangeStatusStudentActivity){ dataStudent ->
            when(dataStudent){
                is States.Loading -> {}
                is States.Success -> {
                    Help.showToast(this@ChangeStatusStudentActivity,dataStudent.data).also {
                        callBack.handleOnBackPressed()
                    }
                }
                is States.Failed -> {Help.showToast(this@ChangeStatusStudentActivity,dataStudent.message.toString())}
                else -> {}
            }
        }
    }

    private val callBack = object: OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            finish()
        }
    }
}