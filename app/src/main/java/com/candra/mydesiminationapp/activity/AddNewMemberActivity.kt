package com.candra.mydesiminationapp.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.candra.mydesiminationapp.R
import com.candra.mydesiminationapp.adapter.AdapterMember
import com.candra.mydesiminationapp.adapter.AdapterStudentMember
import com.candra.mydesiminationapp.data.source.local.LocalMember
import com.candra.mydesiminationapp.data.source.local.toGenerateMemberStudent
import com.candra.mydesiminationapp.data.source.remote.network.States
import com.candra.mydesiminationapp.databinding.ActivityAddNewMemberBinding
import com.candra.mydesiminationapp.helper.Constant
import com.candra.mydesiminationapp.helper.Help
import com.candra.mydesiminationapp.helper.TempDataTwo
import com.candra.mydesiminationapp.helper.viewmodel.student.ResearchDisseminationViewModelStudent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("DEPRECATION")
@AndroidEntryPoint
class AddNewMemberActivity: AppCompatActivity()
{
    private lateinit var binding: ActivityAddNewMemberBinding
    private val researchViewModelStudent by viewModels<ResearchDisseminationViewModelStudent>()
    @Inject
    lateinit var localStore: TempDataTwo
    private val adapterMemberStudent by lazy { AdapterStudentMember() }
    private var selectedPosition = 0
    private var statusId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val arrayOf = arrayOf(0,1,2,3,4)
        setAdapterDropdown(arrayOf)
        showRgChoiceStatus(false)
        lifecycleScope.launch {
            localStore.getToken().collect{
                getAllDataCorrectionMember(it.token)
                setAllDataInMember(it.token)
            }
        }
        setRecyclerView()
        binding.apply {
            intent.getIntExtra(Constant.EXTRA_NEW_MEMBER,0).let { id ->
               val dataList = arrayOf(0,1,2,3,4)
                dpDownNumber.setOnItemClickListener { adapterView, view, i, l ->
                    selectedPosition = dataList[i]
                }
                setAdapterDropdown(dataList)
                rbFilled.isChecked = false
                rbCanceled.isChecked = false
                btnSave.setOnClickListener {
                    validateData(id = id,selectedPosition)
                }
            }
            btnBack.setOnClickListener {
                callBack.handleOnBackPressed()
            }
        }
    }

    private fun setRecyclerView(){
        binding.rvTeamResearch.apply {
            layoutManager = if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            ) GridLayoutManager(this@AddNewMemberActivity,2)
            else LinearLayoutManager(this@AddNewMemberActivity)
            setHasFixedSize(true)
            adapter = adapterMemberStudent
        }
    }

    private fun getAllDataCorrectionMember(token: String){
        intent.getParcelableExtra<LocalMember>(Constant.EXTRA_STUDENT_MEMBER).let { dataMember ->
            showRgChoiceStatus(true)
           when(dataMember?.asX){
               Constant.MEMBER_ZERO -> {
                   val text3 = 0
                   binding.tilNumber.editText?.setText("$text3")
               }
                Constant.MEMBER_ONE -> {
                    val text3 = 1
                    binding.tilNumber.editText?.setText("$text3")
                }
                Constant.MEMBER_TWO -> {
                    val text3 = 2
                    binding.tilNumber.editText?.setText("$text3")
                }
                Constant.MEMBER_THREE -> {
                    val text3 = 3
                    binding.tilNumber.editText?.setText("$text3")
                }
                Constant.MEMBER_FOUR -> {
                    val text3 = 4
                    binding.tilNumber.editText?.setText("$text3")
                }
                else -> {}
            }
            binding.edtNameMember.setText(dataMember?.student?.code)
//            val textPositionMember = when(idPositionMember){
//                1 -> "1"
//                2 -> "2"
//                3 -> "3"
//                4 -> "4"
//                else -> "0"
//            }
            setAdapterDropdown(arrayOf(1,2,3,4))
            binding.apply {
                when(dataMember?.student?.status?.id){
                    0 -> rbFilled.isChecked = true
                    1 -> {
                        rbCanceled.isChecked = true
                        rbFilled.isChecked = false
                    }
                    else -> { rbCanceled.isChecked = true}
                }
                btnSave.setOnClickListener {
                    if (dataMember != null) {
                        when(rgChoiseStatus.checkedRadioButtonId){
                            R.id.rb_filled -> statusId = 0
                            R.id.rb_canceled -> statusId = 3
                        }
                        Log.d("TAG", "getAllDataCorrectionMemberData: token => $token, selectedPosition => $selectedPosition, Code => ${dataMember?.student?.code}," +
                                "Status ID => $statusId")
//                        actionCorrectionMember(token,selectedPosition,dataMember.student.code,statusId)
                    }
                }
            }
        }
    }

//    private fun actionCorrectionMember(token: String,positionSelected: Int,code: String,statusId: Int){
//        researchViewModelStudent.correctionMember(token,positionSelected,code,statusId).observe(this@AddNewMemberActivity){
//            when(it){
//                is States.Loading -> {}
//                is States.Success -> {
//                    Help.showToast(this@AddNewMemberActivity,it.data).also {callBack.handleOnBackPressed()}
//                }
//                is States.Failed -> {Help.showToast(this@AddNewMemberActivity,it.message.toString())}
//                else -> {}
//            }
//        }
//    }

    private fun setAdapterDropdown(list: Array<Int>){
        with(binding){
                val items = list
                val adapter = ArrayAdapter(this@AddNewMemberActivity,android.R.layout.simple_dropdown_item_1line,items)
                dpDownNumber.setAdapter(adapter)
                dpDownNumber.setOnItemClickListener { adapterView, view, position, l ->
                    selectedPosition = position
                }
        }
    }

    private fun validateData(id: Int,position: Int){
        with(binding){
            val researchMember = tilNumber.editText?.text.toString()
            val nimMember = tilNameMember.editText?.text?.trim().toString()
            if (researchMember.isEmpty() && nimMember.isEmpty()){
                Help.showToast(this@AddNewMemberActivity,"NIM dan Nomor Anggota belom diisi silahkan diisi")
            }else{
//                setAllDataAddNewMember(nimMember,id,position)
            }
        }
    }

    private fun setAllDataInMember(token: String){
        with(binding){
            researchViewModelStudent.indexStudent(token).observe(this@AddNewMemberActivity){ dataStudent ->
                when(dataStudent){
                    is States.Loading -> {}
                    is States.Success -> {
                        dataStudent.data.apply {
                            valueIdDesimination.text = "#$id"
                            valueStatusDesimination.text = statusCaption
                            valueTittleDesimination.text = title?: "?"
                            valueAbstract.text = Help.removeHtmlTags(abstract)?: "?"
                            valueKeyword.text = keywords?: "?"
                            valueLecturer.text = lecturer.name?: "?"
                            journal.apply {
                                valueCategoryJournal.text = journalGradeCaption ?: "?"
                                valueNameJournal.text = publisherName?: "?"
                                valueNumberIssn.text = publisherNumber.toString() ?: "?"
                                valuePagePublisher.text = publisherUrl?: "?"
                                valuePageArticle.text = articleUrl?: "?"
                                valueYearJournal.text = "$journalYear"?:"?"
                                valueVolumeJournal.text = "$journalVolume"?: "?"
                                valueNumberJournal.text = journalNumber.toString()?: "?"
                            }
                            valueResearchDesimination.text = document.filename?: ""
                            valueArticle.text = article.filename?: ""
                            valueMaterialPresentation.text = presentation.filename
                            valueFreeAdmin.text = administration.filename
                        }
                        adapterMemberStudent.submitListData(dataStudent.data.members.toGenerateMemberStudent())
                    }
                    is States.Failed -> {
                        Help.showToast(this@AddNewMemberActivity,dataStudent.message)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setAllDataAddNewMember(code: String,idDissemination: Int,position: Int){
        lifecycleScope.launch {
            localStore.getToken().collect{
                if(it.category == Constant.STUDENT) {
                    when(binding.rgChoiseStatus.checkedRadioButtonId){
                        R.id.rb_filled -> selectedPosition = 0
                        R.id.rb_canceled -> selectedPosition = 3
                    }
                    Log.d("TAG", "setAllDataAddNewMember: token => ${it.token} idDissemination => $idDissemination" +
                            "Selected Position => $position Code => $code")
                    researchViewModelStudent.addMemberDissemination(
                        it.token,
                        idDissemination,
                        selectedPosition,
                        code
                    )
                        .observe(this@AddNewMemberActivity) { dataAddMember ->
                            when (dataAddMember) {
                                is States.Loading -> {}
                                is States.Success -> {
                                   Help.showToast(this@AddNewMemberActivity,dataAddMember.data)
                                   startActivity(Intent(this@AddNewMemberActivity,IndexStudentActivity::class.java))
                                       .also { finish() }
                                }

                                is States.Failed -> Help.showToast(this@AddNewMemberActivity,dataAddMember.message)
                                else -> {}
                            }
                        }
                }
            }
        }
    }

    private val callBack = object: OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            finish()
        }
    }

    private fun showRgChoiceStatus(isShow: Boolean)
    {
        with(binding) {
            rgChoiseStatus.visibility = if (isShow) View.VISIBLE else View.GONE
        }
    }
}