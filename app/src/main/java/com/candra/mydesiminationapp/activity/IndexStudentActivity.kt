package com.candra.mydesiminationapp.activity

import android.app.Dialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.candra.mydesiminationapp.R
import com.candra.mydesiminationapp.adapter.AdapterManagementMember
import com.candra.mydesiminationapp.adapter.AdapterStudentMember
import com.candra.mydesiminationapp.data.source.local.LocalMember
import com.candra.mydesiminationapp.data.source.local.toGenerateMemberStudent
import com.candra.mydesiminationapp.data.source.remote.network.GetTokenLecturer
import com.candra.mydesiminationapp.data.source.remote.network.States
import com.candra.mydesiminationapp.databinding.ActivityAddNewMemberBinding
import com.candra.mydesiminationapp.databinding.ActivityIndexStudentBinding
import com.candra.mydesiminationapp.helper.Constant
import com.candra.mydesiminationapp.helper.Help
import com.candra.mydesiminationapp.helper.TempDataTwo
import com.candra.mydesiminationapp.helper.viewmodel.student.ResearchDisseminationViewModelStudent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("DEPRECATION")
@AndroidEntryPoint
class IndexStudentActivity: AppCompatActivity()
{
    private lateinit var binding: ActivityIndexStudentBinding
    private val adapterMember by lazy { AdapterStudentMember() }
    private val adapterMemberManagementTeam by lazy { AdapterManagementMember(::onCorrection) }
    private val researchDisseminationViewModel by viewModels<ResearchDisseminationViewModelStudent>()
    @Inject
    lateinit var localStore: TempDataTwo
    private val listArray = arrayOf(0,1,2,3,4)
    private var positionId = 0
    private var statusId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIndexStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerViewAdapterMember()
        setRecyclerViewAdapterMemberManagementTeam()
        binding.apply {
            imageLogo.load(Constant.URL_IMAGE_SISKA_DISSEMINATION)
            imageLogoAccept.load(Constant.URL_IMAGE_SISKA_DISSEMINATION)
        }
        Help.setToolbar(1,supportActionBar,"Index Dessiminasi")
        lifecycleScope.launch {
            localStore.getToken().collect{
                if (it.category == Constant.STUDENT){
                    if(it.statusId == 1) {
                        showContainerAcceptMember(false)
                        getAllData(it.token,it.code)
                        Log.d("CheckToken", "onCreate: Token => ${it.token} category => ${it.category} dan status => ${it.statusId}")
                    }else{
                        showContainerAcceptMember(true)
                        if (it.statusId == 0){
                            Log.d("TAG", "onCreateMemberId0: statusId => ${it.statusId}")
                            setDataMember(it.token,it.code)
                            showAll(true)
                        }else{
                            Log.d("TAG", "onCreateMemberId2: statusId => ${it.statusId}")
                            showAll(false)
                        }
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout_lecturer){
            lifecycleScope.launch {
                localStore.putToken(GetTokenLecturer("","","",5,""))
                    .also { startActivity(Intent(this@IndexStudentActivity,TempActivity::class.java))
                        .also { finish() }}
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showContainerAcceptMember(isShow: Boolean){
        with(binding){
            containerAcceptMember.visibility = if (isShow) View.VISIBLE else View.GONE
            containerIndexStudent.visibility = if (isShow) View.GONE else View.VISIBLE
        }
    }

    private fun setDataMember(token: String,code: String) {
        with(binding) {
            researchDisseminationViewModel.indexStudent(token)
                .observe(this@IndexStudentActivity) { dataObserver ->
                    when (dataObserver) {
                        is States.Loading -> {}
                        is States.Success -> {
                            val data = dataObserver.data.members
                            for (member in data) {
                                if (code.contains(member.student.code,true)){
                                    btnAccept.setOnClickListener {
                                    setAcceptMemberDissemination(member.id,token)
                                        Log.d("TAG", "setDataMemberAccept: Kode => $code Token => $token id => ${member.id}")
                                    }
                                    btnReject.setOnClickListener {
                                        setRejectMember(token,member.id)
                                        Log.d("TAG", "setDataMemberAccept: Kode => $code Token => $token id => ${member.id}")
                                    }
                                    break
                                }
                                if (member.asX == Constant.LEADER_DISSEMINATAION) {
                                    valueStatusDesiminationAccept.text = member.student.name
                                }
                            }

                            valueIdDesiminationAccept.text =
                                dataObserver.data.statusCaption ?: ""
                            valueTitleDesimination.text = dataObserver.data.title ?: "?"
                            valueLeaderDesimination.text =
                                dataObserver.data.lecturer.name ?: ""
                        }

                        is States.Failed -> Help.showToast(
                            this@IndexStudentActivity,
                            dataObserver.message
                        )

                        else -> {}
                    }
                }
        }
    }

    private fun setRejectMember(token:String, id: Int){
        researchDisseminationViewModel.rejectMemberDissemination(token,id)
            .observe(this@IndexStudentActivity){
                when(it){
                    is States.Loading -> {showProgressBarForBtnRejectAndBtnAccept(true)}
                    is States.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Help.showToast(this@IndexStudentActivity,"Berhasil menolak dessiminasi, Silahkan login kembali")
                        lifecycleScope.launch {
                            localStore.putToken(GetTokenLecturer("","","",5,""))
                                .also { startActivity(Intent(this@IndexStudentActivity,TempActivity::class.java))
                                    .also { finish() }}
                        }
                    }
                    is States.Failed -> {
                        Help.showToast(this@IndexStudentActivity,it.message)
                        showAll(false)
                    }
                    else -> {}
                }
            }
    }

    private fun setAcceptMemberDissemination(id: Int, token: String) {
        researchDisseminationViewModel.acceptMemberToDissemination(token, id)
            .observe(this@IndexStudentActivity) {
                when (it) {
                    is States.Loading -> showProgressBarForBtnRejectAndBtnAccept(true)
                    is States.Success -> {
                        Help.showToast(this@IndexStudentActivity, "Berhasil, Silahkan login kembali")
                        lifecycleScope.launch {
                            localStore.putToken(GetTokenLecturer("","","",5,""))
                        }.also { startActivity(Intent(this@IndexStudentActivity,TempActivity::class.java))
                            .also { finish() }}
                    }

                    is States.Failed ->{
                        showAll(false)
                        showContainerAcceptMember(true)
                        Help.showToast(this@IndexStudentActivity, it.message)
                    }
                    else -> {}
                }
            }
    }

    private fun showAll(isShow: Boolean){
        with(binding){
            titleSubmissionAsResearchMember.visibility = if (isShow) View.VISIBLE else View.GONE
            titleAcceptMember.visibility = if(isShow) View.VISIBLE else View.GONE
            container.visibility = if(isShow) View.VISIBLE else View.GONE
        }
    }

    private fun showProgressBarForBtnRejectAndBtnAccept(isShow: Boolean){
        binding.apply {
            progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
            btnAccept.visibility = if(isShow) View.GONE else View.VISIBLE
            btnReject.visibility = if (isShow) View.GONE else View.VISIBLE
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun toLogActivity(id: Int,token:String){
        Intent(this@IndexStudentActivity,LogActivity::class.java).apply {
            putExtra(Constant.EXTRA_STUDENT_MEMBER_ID,id)
            putExtra(Constant.EXTRA_TOKEN,token)
        }.also { startActivity(it) }
    }

    private fun onCorrection(localMember: LocalMember){
        lifecycleScope.launch {
            localStore.getToken().collect{
                if (it.category == Constant.STUDENT) showDialogCorrectionMember(localMember,it.token)
            }
        }
    }

    private fun getAllData(token: String, code: String){
        with(binding){
            Log.d("CheckToken", "getAllData: $token")
            researchDisseminationViewModel.indexStudent(token).observe(this@IndexStudentActivity){dataStudent ->
                when(dataStudent) {
                    is States.Loading -> {}
                    is States.Success -> {
                        Help.visibleDeterminationDecision(dataStudent.data.statusCaption,binding.determinationDecision,binding.valueDeterminationDecision)
                        val memberData = dataStudent.data.members.toGenerateMemberStudent()
                        for(data in memberData){
                            if (data.asX == Constant.LEADER_DISSEMINATAION && code.contains(data.student.code,true)) {
                                titleResearchDesimination.text = getString(R.string.assignment_as_chair_researcher,Constant.LEADER_DISSEMINATAION.uppercase())
                                containerResearchTeamManagement.visibility = View.VISIBLE
                                btnNewMember.visibility = View.VISIBLE
                                btnChangeStatus.visibility = View.VISIBLE
                                btnEdit.visibility = View.VISIBLE

                            }else{
                                containerResearchTeamManagement.visibility = View.GONE
                                btnNewMember.visibility = View.GONE
                                btnChangeStatus.visibility = View.GONE
                                researchDesimination.visibility = View.GONE
                                btnEdit.visibility = View.GONE
                                containerResearchTeamManagement.visibility = View.GONE
                                titleResearchDesimination.text = getString(R.string.assignment_as_chair_researcher,Constant.MEMBER_DISSEMINATION.uppercase())
                            }
                            break
                        }
                        if(memberData.isEmpty()) Help.showToast(this@IndexStudentActivity,"Tidak ada peneliti disini")
                        else {
                            adapterMember.submitListData(memberData)
                            adapterMemberManagementTeam.submitListData(memberData)
                        }
                        val dataJournal = dataStudent.data.journal
                        if (dataJournal.journalGradeCaption.isEmpty()) cardPublisher.visibility = View.GONE
                        else cardPublisher.visibility = View.VISIBLE
                        article.text = "Artikel"
                        val getAllData = dataStudent.data
                        getAllData.apply {
                            setComponent(id,statusCaption,title,abstract,keywords,lecturer.name,
                            journal.journalGradeCaption,journal.publisherName,journal.publisherNumber,
                            journal.publisherUrl,journal.articleUrl,journal.journalYear,journal.journalVolume,
                            journal.journalNumber,document.filename,article.filename,presentation.filename,administration.filename)
                        }
                        btnNote.setOnClickListener {
                            toLogActivity(dataStudent.data.id,token)
                        }
                        btnNewMember.setOnClickListener {
                            showDialogAddNewMember(token,dataStudent.data.id)
                        }
                        btnChangeStatus.setOnClickListener {
                            toChangeStatusStudentActivity(dataStudent.data.id)
                        }
                        btnEdit.setOnClickListener {
                            Intent(this@IndexStudentActivity,EditMemberActivity::class.java).apply {
                                putExtra(Constant.EXTRA_JOURNAL,"Jurnal")
                                putExtra(Constant.EXTRA_GRADE_JOURNAL,getAllData.journal.journalGradeCaption)
                                putExtra(Constant.EXTRA_STUDENT_ID,getAllData.id)
                            }.also { startActivity(it) }
                        }
                    }
                    is States.Failed -> {

                    }
                    else -> {}
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun toChangeStatusStudentActivity(id: Int){
        Intent(this@IndexStudentActivity,ChangeStatusStudentActivity::class.java).apply {
            putExtra(Constant.EXTRA_CHANGE_STATUS_MEMBER,id)
        }.also { startActivity(it) }
    }


    private fun setRecyclerViewAdapterMember(){
        binding.rvTeamResearch.apply {
            layoutManager = if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            ) GridLayoutManager(this@IndexStudentActivity,2)
            else LinearLayoutManager(this@IndexStudentActivity)
            setHasFixedSize(true)
            adapter = adapterMember
        }
    }

    private fun setRecyclerViewAdapterMemberManagementTeam()
    {
        binding.rvResearchTeamManagement.apply {
            layoutManager = if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            ) GridLayoutManager(this@IndexStudentActivity,2)
            else LinearLayoutManager(this@IndexStudentActivity)
            setHasFixedSize(true)
            adapter = adapterMemberManagementTeam
        }
    }


    private fun setComponent(
        idDissemination: Int,status: String,titleResearch: String,abstract: String,keyword: String,
        lecturer: String,categoryJournal: String,nameJournal: String,numberISSN: String,pagePublisher: String,
        pageArticle: String,yearJournal: Int,journalVolume: Int,journalNumber: Int,researchDissemination: String,
        article: String,presentation: String,administration: String,
    ){
        binding.apply {
            valueIdDesimination.text = "#$idDissemination"?:"?"
            valueStatusDesimination.text = status?:"?"
            valueTittleDesimination.text = titleResearch?: "?"
            valueAbstract.text = Help.removeHtmlTags(abstract?: "")
            valueKeyword.text = keyword?: "?"
            valueLecturer.text = lecturer
            valueCategoryJournal.text = categoryJournal
            valueNameJournal.text = nameJournal
            valueNumberIssn.text = numberISSN
            valuePagePublisher.text = pagePublisher
            valuePageArticle.text = pageArticle
            valueYearJournal.text = "$yearJournal"
            valueVolumeJournal.text = "$journalVolume"
            valueNumberJournal.text = "$journalNumber"
            valueResearchDesimination.text = researchDissemination?: "Desiminasi Penelitian belum ada"
            valueArticle.text = article?:"Artikel belum ada"
            valueMaterialPresentation.text = presentation?:"Bahan presentasi belum ada"
            valueFreeAdmin.text = administration?:"Bebas Administrasi belum ada"
        }
    }

    // Bug in Correction Member
    private fun showDialogCorrectionMember(localMember: LocalMember,token: String){
        val dialog = Dialog(this@IndexStudentActivity)
        val dialogBinding = ActivityAddNewMemberBinding.inflate(layoutInflater)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(dialogBinding.root)
        }
        setRecyclerViewAdapterMemberForAddNewMemberAndCorrectionMember(dialogBinding)
        with(dialogBinding){
            when(localMember.asX){
                Constant.MEMBER_ZERO -> {
                    positionId = 0
                    tilNumber.editText?.setText("$positionId")
                }
                Constant.MEMBER_ONE -> {
                    positionId = 1
                    tilNumber.editText?.setText("$positionId")
                }
                Constant.MEMBER_TWO -> {
                    positionId = 2
                    tilNumber.editText?.setText("$positionId")
                }
                Constant.MEMBER_THREE -> {
                    positionId = 3
                    tilNumber.editText?.setText("$positionId")
                }
                Constant.MEMBER_FOUR -> {
                    positionId = 4
                    tilNumber.editText?.setText("$positionId")
                }
                else -> {}
            }
            edtNameMember.setText(localMember.student.code)
            when(localMember.student.status.id){
                0 -> rbFilled.isChecked = true
                1 -> {
                    rbCanceled.isChecked = false
                    rbFilled.isChecked = false
                }
                else -> rbCanceled.isChecked = true
            }
            dpDownNumber.setOnItemClickListener { adapterView, view, i, l ->
                positionId = listArray[i]
            }
            btnSave.setOnClickListener {
                if (rgChoiseStatus.checkedRadioButtonId > 0){
                    when(rgChoiseStatus.checkedRadioButtonId){
                        R.id.rb_filled -> statusId = 0
                        R.id.rb_canceled -> statusId = 3
                    }
                }
                val codeStudent = tilNameMember.editText?.text.toString()
                researchDisseminationViewModel.indexStudent(token).observe(this@IndexStudentActivity){student ->
                    when(student){
                        is States.Loading -> {}
                        is States.Success -> {
                            val memberData = student.data.members.toGenerateMemberStudent()
                            for(data in memberData){
                                if (codeStudent.equals(localMember.student.code,true)){
//                                    actionCorrectionMember(token,positionId,localMember.student.code,statusId,dialog,data.id)
                                    Log.d("TAG", "showDialogCorrectionMember: Token => $token," +
                                            "selectedPosition => $positionId, Code => ${localMember.student.code}," +
                                            "StatusId => $statusId, id => ${data.id}")
                                }
                                break
                            }
                        }
                        is States.Failed -> {}
                        else -> {}
                    }
                }

            }
            btnBack.setOnClickListener {
                dialog.dismiss()
            }
        }
        setAdapterDropDown(listArray,dialogBinding)
        setAllDataInMember(token,dialogBinding)
        dialog.apply {
            show()
            window?.apply {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                attributes.windowAnimations = R.style.DialogAnimation
                setGravity(Gravity.BOTTOM)
            }
        }
    }

    private fun showDialogAddNewMember(token:String,id: Int){
        val dialog = Dialog(this@IndexStudentActivity)
        val dialogBinding = ActivityAddNewMemberBinding.inflate(layoutInflater)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(dialogBinding.root)
        }
        setAllDataInMember(token,dialogBinding)
        setAdapterDropDown(listArray,dialogBinding)
        setRecyclerViewAdapterMemberForAddNewMemberAndCorrectionMember(dialogBinding)
        with(dialogBinding){
            dpDownNumber.setOnItemClickListener { adapterView, view, i, l ->
                positionId = listArray[i]
            }
            rbCanceled.visibility = View.GONE
            btnSave.setOnClickListener {
                val researchMember = tilNumber.editText?.text?.trim().toString()
                val nimMember = tilNameMember.editText?.text?.trim().toString()
                if (researchMember.isEmpty() && nimMember.isEmpty()){
                    Help.showToast(this@IndexStudentActivity,"NIM dan Nomor Anggota belom diisi silahkan diisi")
                }else{
                    Log.d("TAG", "showDialogAddNewMember: Token => $token, Code => $nimMember, idDissemination => $id, positionId => $positionId")
                    setAllDataAddMember(token,nimMember,id,positionId,dialog)
                }
            }
            btnBack.setOnClickListener {
                dialog.dismiss()
            }

        }
        dialog.apply {
            show()
            window?.apply {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                attributes.windowAnimations = R.style.DialogAnimation
                setGravity(Gravity.BOTTOM)
            }
        }
    }

    private fun setAllDataAddMember(token: String,code: String,idDissemination: Int,position: Int,dialog: Dialog){
        researchDisseminationViewModel.addMemberDissemination(token,idDissemination,position,code).observe(this@IndexStudentActivity){
            when(it){
                is States.Loading -> {}
                is States.Success -> {
                    Help.showToast(this@IndexStudentActivity,it.data)
                    dialog.dismiss()
                }
                is States.Failed -> Help.showDialog(this@IndexStudentActivity,it.message)
                else -> {}
            }
        }
    }

    private fun actionCorrectionMember(token: String,positionSelected: Int,code: String,statusId: Int,dialog: Dialog,id: Int){
        researchDisseminationViewModel.correctionMember(token,positionSelected,code,statusId,id).observe(this@IndexStudentActivity){
            when(it){
                is States.Loading -> {}
                is States.Success -> {
                    Help.showToast(this@IndexStudentActivity,it.data).also { dialog.dismiss() }
                }
                is States.Failed -> {Help.showDialog(this@IndexStudentActivity,it.message)}
                else -> {}
            }
        }
    }

    private fun setAdapterDropDown(list: Array<Int>,binding: ActivityAddNewMemberBinding){
        with(binding){
            val items = list
            val adapter = ArrayAdapter(this@IndexStudentActivity,android.R.layout.simple_dropdown_item_1line,items)
            dpDownNumber.setAdapter(adapter)
        }
    }

    private fun setRecyclerViewAdapterMemberForAddNewMemberAndCorrectionMember(binding: ActivityAddNewMemberBinding){
        binding.rvTeamResearch.apply {
            layoutManager = if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            ) GridLayoutManager(this@IndexStudentActivity,2)
            else LinearLayoutManager(this@IndexStudentActivity)
            setHasFixedSize(true)
            adapter = adapterMember
        }
    }

    private fun setAllDataInMember(token: String,binding: ActivityAddNewMemberBinding){
        with(binding){
            researchDisseminationViewModel.indexStudent(token).observe(this@IndexStudentActivity){ dataStudent ->
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
                        adapterMember.submitListData(dataStudent.data.members.toGenerateMemberStudent())
                    }
                    is States.Failed -> {
                        Help.showToast(this@IndexStudentActivity,dataStudent.message)
                    }
                    else -> {}
                }
            }
        }
    }
}