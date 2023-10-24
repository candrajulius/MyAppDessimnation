package com.candra.mydesiminationapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isEmpty
import androidx.lifecycle.lifecycleScope
import com.candra.mydesiminationapp.R
import com.candra.mydesiminationapp.databinding.ActivityLoginBinding
import com.candra.mydesiminationapp.helper.Help
import com.candra.mydesiminationapp.helper.viewmodel.Lecturer.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        loginBinding.btnLoginLecturer.setOnClickListener {
            validateEmailAndPassword()
        }
        Help.setToolbar(0,supportActionBar,"Login Dosen")
        loginResponseViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            toTemptActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toTemptActivity(){
        startActivity(Intent(this@LoginActivity,TempActivity::class.java))
            .also { finish() }
    }

    private fun loginResponseViewModel(){

        loginViewModel.loading.observe(this@LoginActivity){
            if (it) showProgressBar(true) else showProgressBar(false)
        }

        loginViewModel.messageError.observe(this@LoginActivity){
            Help.showDialog(this@LoginActivity,it)
        }
        loginViewModel.messageSuccess.observe(this@LoginActivity){
            Help.showToast(this@LoginActivity,it)
        }
        loginViewModel.success.observe(this@LoginActivity)
        { if (it) setSuccessLogin() else setFailedLogin()}
    }

    private fun validateEmailAndPassword()
    {
        with(loginBinding){
            val emailLecturer = tilUsername.editText?.text.toString().lowercase().trim()
            val password = tilPassword.editText?.text.toString().trim()
           if(tilUsername.isEmpty() && tilPassword.isEmpty()){
                Help.showToast(this@LoginActivity,getString(R.string.email_and_password_not_blank))
            }else{
                lifecycleScope.launch {
                    loginViewModel.loginLecturerDesimination(this@LoginActivity,emailLecturer,password)
                }
            }
        }
    }

    private fun setFailedLogin(){
        showProgressBar(false)
        Help.setClearText(loginBinding.tilUsername,loginBinding.tilPassword)
    }

    private fun setSuccessLogin(){
        startActivity(Intent(this@LoginActivity,LecturerActivity::class.java))
            .also {
                finish()
                showProgressBar(false)
                Help.setClearText(tilInputEmail = loginBinding.tilUsername, tilInputPassword = loginBinding.tilPassword)
            }
    }

    private fun showProgressBar(isShow: Boolean){
        with(loginBinding){
            progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
            btnLoginLecturer.visibility = if (isShow) View.GONE else View.VISIBLE
        }
    }
}