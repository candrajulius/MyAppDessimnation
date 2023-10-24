package com.candra.mydesiminationapp.activity

import android.annotation.SuppressLint
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
import com.candra.mydesiminationapp.adapter.DesiminationLogsAdapter
import com.candra.mydesiminationapp.data.source.remote.network.States
import com.candra.mydesiminationapp.databinding.ActivityLogDesiminationBinding
import com.candra.mydesiminationapp.helper.Constant
import com.candra.mydesiminationapp.helper.Help
import com.candra.mydesiminationapp.helper.TempDataTwo
import com.candra.mydesiminationapp.helper.viewmodel.Lecturer.ResearchDisseminationViewModel
import com.candra.mydesiminationapp.helper.viewmodel.administrator.ResearchDisseminationAdminViewModel
import com.candra.mydesiminationapp.helper.viewmodel.student.ResearchDisseminationViewModelStudent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LogActivity: AppCompatActivity()
{
    private lateinit var logBinding: ActivityLogDesiminationBinding
    private val logDisseminationViewModel by viewModels<ResearchDisseminationViewModel>()
    private val logDisseminationViewModelStudent by viewModels<ResearchDisseminationViewModelStudent>()
    private val logDisseminationViewModelAdmin by viewModels<ResearchDisseminationAdminViewModel>()
    @Inject
    lateinit var localStore: TempDataTwo
    private val adapterLog by lazy { DesiminationLogsAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logBinding = ActivityLogDesiminationBinding.inflate(layoutInflater)
        setContentView(logBinding.root)
        Help.setToolbar(1,supportActionBar,Constant.TITTLE_LOG)
        addToRecyclerView()
        getDataIdFromLecturerActivity()
        getDataIdAndTokenFromIndexStudentActivity()
        receiveDataFromShowDisseminationAdmin()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getDataIdFromLecturerActivity(){
        lifecycleScope.launch {
            localStore.getToken().collect{ tokenLecturer ->
                if (tokenLecturer.category == Constant.LECTURER) {
                    with(logBinding) {
                        val getId = intent.getIntExtra(Constant.EXTRA_STUDENT_ID, 0)
                        Log.d("RemoteId", "getDataIdFromLecturerActivity: $getId")
                        logDisseminationViewModel.getLogLecturerItem(
                            getId,
                            tokenLecturer.token ?: ""
                        ).observe(this@LogActivity)
                        {
                            when (it) {
                                is States.Loading -> Help.showProgressBar(
                                    true,
                                    listItemLog,
                                    progrressBarLog
                                )

                                is States.Success -> {
                                    if (it.data.isEmpty()) {
                                        Help.showProgressBar(false, listItemLog, progrressBarLog)
                                        showEmptyText(true)
                                    } else {
                                        adapterLog.submitListData(it.data)
                                        Help.showProgressBar(false, listItemLog, progrressBarLog)
                                    }
                                }

                                is States.Failed -> {
                                    Help.showProgressBar(false, listItemLog, progrressBarLog)
                                    Help.showToast(this@LogActivity, it.message.toString())
                                }

                                else -> {
                                    Help.showProgressBar(false, listItemLog, progrressBarLog)
                                    mtvEmpty.visibility = View.VISIBLE
                                    mtvEmpty.text = Constant.EMPTY_TEXT
                                }
                            }
                        }
                    }
                }
            }
        }
        logBinding.btnBack.setOnClickListener {
            callBack.handleOnBackPressed()
        }
    }

    private fun receiveDataFromShowDisseminationAdmin(){
        logBinding.apply {
            val token = intent.getStringExtra(Constant.EXTRA_TOKEN_ADMIN)
            val id = intent.getIntExtra(Constant.POSITON_ID,0)
            if (token != null){
                logDisseminationViewModelAdmin.getLogResearchDisseminationAdministrator(token,id).observe(this@LogActivity){
                    when(it){
                        is States.Loading -> Help.showProgressBar(true,listItemLog,progrressBarLog)
                        is States.Success -> {
                            Help.showProgressBar(false,listItemLog,progrressBarLog)
                            adapterLog.submitListData(it.data)
                        }
                        is States.Failed -> {
                            Help.showToast(this@LogActivity,"Terjadi kesalahan pada server")
                            Help.showProgressBar(false,listItemLog,progrressBarLog)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun getDataIdAndTokenFromIndexStudentActivity(){
        with(logBinding){
            val getIdStudent = intent.getIntExtra(Constant.EXTRA_STUDENT_MEMBER_ID,0)
            val tokenStudent = intent.getStringExtra(Constant.EXTRA_TOKEN)
            if (tokenStudent != null)
            {
                Log.d("TAG", "getDataIdAndTokenFromIndexStudentActivity: Id => $getIdStudent dan token => $tokenStudent")
                logDisseminationViewModelStudent.logStudent(tokenStudent,getIdStudent).observe(this@LogActivity){
                    when(it){
                        is States.Loading -> Help.showProgressBar(
                            true,
                            listItemLog,
                            progrressBarLog
                        )
                        is States.Success -> {
                            adapterLog.submitListData(it.data)
                            Help.showProgressBar(false,listItemLog,progrressBarLog)
                        }
                        is States.Failed -> {
                            Help.apply {
                                showProgressBar(false,listItemLog,progrressBarLog)
                                showToast(this@LogActivity,it.message)
                            }
                        }
                        else -> {}
                    }
                }
                btnBack.setOnClickListener {
                    callBack.handleOnBackPressed()
                }
            }
        }
    }

    private fun showEmptyText(isShowEmpty: Boolean){
        logBinding.apply {
            mtvEmpty.visibility = if (isShowEmpty) View.VISIBLE else View.GONE
            progrressBarLog.visibility = if (isShowEmpty) View.GONE else View.VISIBLE
            listItemLog.visibility = if (isShowEmpty) View.GONE else View.VISIBLE
        }
    }

   private val callBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Aksi yang ingin Anda lakukan ketika tombol kembali ditekan
            // Misalnya, perintah untuk menutup aplikasi
            finish()
        }
    }

    private fun addToRecyclerView(){
        logBinding.listItemLog.apply {
            layoutManager = if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            ) GridLayoutManager(this@LogActivity,2)
            else LinearLayoutManager(this@LogActivity)
            setHasFixedSize(true)
            adapter = adapterLog
        }
    }
}