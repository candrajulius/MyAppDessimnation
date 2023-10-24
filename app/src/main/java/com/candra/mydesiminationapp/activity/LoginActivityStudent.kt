package com.candra.mydesiminationapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.lifecycle.lifecycleScope
import com.candra.mydesiminationapp.R
import com.candra.mydesiminationapp.data.source.remote.network.GetTokenLecturer
import com.candra.mydesiminationapp.data.source.remote.network.States
import com.candra.mydesiminationapp.databinding.ActivityLoginBinding
import com.candra.mydesiminationapp.helper.Constant
import com.candra.mydesiminationapp.helper.Help
import com.candra.mydesiminationapp.helper.TempDataTwo
import com.candra.mydesiminationapp.helper.viewmodel.student.LoginViewModelStudent
import com.candra.mydesiminationapp.helper.viewmodel.student.ResearchDisseminationViewModelStudent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class LoginActivityStudent: AppCompatActivity(){
    private lateinit var binding: ActivityLoginBinding
    private val researchDisseminationViewModelStudent by viewModels<ResearchDisseminationViewModelStudent>()
    private val loginViewModel by viewModels<LoginViewModelStudent>()
    @Inject
    lateinit var localStore: TempDataTwo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.btnLoginLecturer.setOnClickListener {
            validateEmailAndPassword()
        }
        Help.setToolbar(0,supportActionBar,getString(R.string.login_student))
        binding.mtvTitle.text = getString(R.string.login_student)
        binding.tilUsername.hint = "NIM"
        binding.tilPassword.hint = "Password Mahasiswa"
        setContentView(binding.root)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) toTemptActivity()
        return super.onOptionsItemSelected(item)
    }

    private fun toTemptActivity(){
        startActivity(Intent(this@LoginActivityStudent,TempActivity::class.java))
            .also { finish() }
    }


    private fun validateEmailAndPassword()
    {
        with(binding){
            val codeStudent = tilUsername.editText?.text.toString().lowercase().trim()
            val password = tilPassword.editText?.text.toString().trim()
            if(codeStudent.isEmpty() && codeStudent.isEmpty()){
                com.candra.mydesiminationapp.helper.Help.showToast(this@LoginActivityStudent,getString(R.string.email_and_password_not_blank))
            }else{
                loginViewModel.loginStudent(codeStudent,password,Constant.FCM_TOKEN).observe(this@LoginActivityStudent){
                    when(it){
                        is States.Loading -> showProgressBar(true)
                        is States.Success -> {
                            showProgressBar(false)
                            researchDisseminationViewModelStudent.indexStudent(it.data.token).observe(this@LoginActivityStudent){ dataStudent ->
                                when(dataStudent){
                                    is States.Loading -> {}
                                    is States.Success -> {
                                        val data = dataStudent.data.members
                                        for(member in data){
                                            member.let { memberStudent ->
                                                if (codeStudent == memberStudent.student.code){
                                                    Log.d("TAG", "validateEmailAndPassword: ${memberStudent.student.code}")
                                                    memberStudent(it.data.token,codeStudent,memberStudent.student.status.id,Constant.FCM_TOKEN)
                                                    toIndexDisseminationActivity()
                                                }
                                            }
                                        }
                                    }
                                    is States.Failed -> {
                                        Log.d("TAG", "validateEmailAndPassword: ${dataStudent.message}")
                                        Help.showToast(this@LoginActivityStudent,dataStudent.message)
                                    }
                                    else -> {}
                                }
                            }
                        }
                        is States.Failed -> {
                            Help.showToast(this@LoginActivityStudent,it.message)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun memberStudent(token: String,codeStudent: String,statusId: Int,tokenFcm: String){
        lifecycleScope.launch {
            localStore.putToken(GetTokenLecturer(token,Constant.STUDENT,codeStudent,statusId,tokenFcm))
        }
    }

    private fun toAcceptDisseminationActivity()
    {
        startActivity(Intent(this@LoginActivityStudent,AcceptMemberActivity::class.java))
            .also { finish() }
    }

    private fun toIndexDisseminationActivity(){
        startActivity(Intent(this@LoginActivityStudent,IndexStudentActivity::class.java))
            .also { finish() }
    }

    private fun showProgressBar(isShow: Boolean){
        with(binding){
            progressBar.visibility = if (isShow) android.view.View.VISIBLE else android.view.View.GONE
            btnLoginLecturer.visibility = if (isShow) android.view.View.GONE else android.view.View.VISIBLE
        }
    }
}