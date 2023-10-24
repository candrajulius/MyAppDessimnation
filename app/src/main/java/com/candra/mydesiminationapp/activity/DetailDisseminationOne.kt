@file:Suppress("DEPRECATION")

package com.candra.mydesiminationapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.candra.mydesiminationapp.R
import com.candra.mydesiminationapp.data.source.local.LocalIndexLecturerItem
import com.candra.mydesiminationapp.data.source.remote.network.States
import com.candra.mydesiminationapp.databinding.DialogShowDesiminationBinding
import com.candra.mydesiminationapp.helper.Constant
import com.candra.mydesiminationapp.helper.Help
import com.candra.mydesiminationapp.helper.TempDataTwo
import com.candra.mydesiminationapp.helper.viewmodel.Lecturer.ResearchDisseminationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailDisseminationOne : AppCompatActivity(){
    private lateinit var disseminationOneBinding: DialogShowDesiminationBinding
    @Inject
    lateinit var localStore: TempDataTwo
    private val detailDisseminationVieModel by viewModels<ResearchDisseminationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disseminationOneBinding = DialogShowDesiminationBinding.inflate(layoutInflater)
        setContentView(disseminationOneBinding.root)
        getDataAll()
        Help.setToolbar(1,supportActionBar,getString(R.string.research_desimination))
    }
    private fun getDataAll(){
        lifecycleScope.launch {
            localStore.getToken().collect{ tokenLecturer ->
                if (tokenLecturer.category == Constant.LECTURER){
                    intent.getParcelableExtra<LocalIndexLecturerItem>(Constant.EXTRA_STUDENT_ID)?.let {
                        responseViewModel(it.id,it.statusCaption,it.headName,tokenLecturer.token)
                    }
                }
            }
        }
    }

    private fun responseViewModel(id: Int,statusCaption: String,leader: String,token: String)
    {
        detailDisseminationVieModel.showLecturerDissemination(id,token).observe(this){
            when(it){
                is States.Loading -> showProgressBar(true)
                is States.Success -> {
                    with(disseminationOneBinding){
                        showProgressBar(false)
                        if (statusCaption == Constant.STATUS_NEW){
                            valueIdDesiminationResearch.text = "#$id"
                            valueStatusDesimination.text = statusCaption
                            valueLecturer.text = it.data.lecturer.name
                            valueLeaderDesimination.text = leader
                            btnChangeStatusToRegistration.setOnClickListener {
                                actionChangeStatusRegister(id,token)
                            }
                        }else{
                            valueIdDesiminationResearch.text = id.toString()
                            valueStatusDesimination.text = statusCaption
                            valueLecturer.text = it.data.lecturer.name
                            valueLeaderDesimination.text = leader
                            btnChangeStatusToRegistration.visibility = View.GONE
                            btnBlankForm.text = getString(R.string.change_status_back_new)
                            btnBlankForm.setOnClickListener {
                                setBackToNewStatus(id,token)
                            }
                        }
                        btnBack.setOnClickListener {
                            backToBeforeActivity()
                        }
                        btnReview.setOnClickListener {
                            Help.showToast(this@DetailDisseminationOne,"Fitur masih dalam tahap pengembangan")
                        }
                        btnEdit.setOnClickListener {
                            editActivity(leader)
                        }
                        btnNote.setOnClickListener {
                            setDataNote(id)
                        }
                    }
                }
                is States.Failed -> {
                    Help.showToast(this@DetailDisseminationOne,it.message)
                    showProgressBar(false)
                }
                else -> {}
            }
        }
    }

    private fun editActivity(leader: String){
        Intent(this@DetailDisseminationOne,AddLeaderDisseminationActivity::class.java).apply {
            putExtra(Constant.EXTRA_STUDENT_ID,leader)
        }.also { startActivity(it).also { finish() } }
    }

    private fun setDataNote(id: Int){
        Intent(this@DetailDisseminationOne,LogActivity::class.java).apply {
            putExtra(Constant.EXTRA_STUDENT_ID,id)
        }.also { startActivity(it) }
    }

    private fun setBackToNewStatus(id: Int,token: String){
        detailDisseminationVieModel.backStatusToNew(id,token).observe(this@DetailDisseminationOne){
            when(it){
                is States.Loading -> {}
                is States.Success -> {
                    Help.showToast(this@DetailDisseminationOne,it.data).also {backToBeforeActivity()}
                }
                is States.Failed -> Help.showDialog(this@DetailDisseminationOne,it.message)
                else -> {}
            }
        }
    }

    private fun backToBeforeActivity(){
        startActivity(Intent(this@DetailDisseminationOne,LecturerActivity::class.java))
            .also { finish() }
    }

    private fun actionChangeStatusRegister(id: Int,token: String){
        detailDisseminationVieModel.changeStatusToDraftRegister(id,token).observe(this@DetailDisseminationOne){
            when (it){
                is States.Loading -> {}
                is States.Success -> {
                    backToBeforeActivity()
                    Help.showToast(this@DetailDisseminationOne,it.data)
                }
                is States.Failed -> Help.showToast(this@DetailDisseminationOne,it.message)
                else -> {}
            }
        }
    }

    private fun showProgressBar(isShow: Boolean){
        with(disseminationOneBinding){
            progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
            container1.visibility = if (isShow) View.GONE else View.VISIBLE
        }
    }
}