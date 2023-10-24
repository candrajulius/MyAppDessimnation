package com.candra.mydesiminationapp.activity.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.candra.mydesiminationapp.R
import com.candra.mydesiminationapp.data.source.remote.network.GetTokenLecturer
import com.candra.mydesiminationapp.data.source.remote.network.States
import com.candra.mydesiminationapp.databinding.ActivityLoginAdministratorBinding
import com.candra.mydesiminationapp.helper.Constant
import com.candra.mydesiminationapp.helper.Help
import com.candra.mydesiminationapp.helper.TempDataTwo
import com.candra.mydesiminationapp.helper.viewmodel.administrator.LoginViewModelAdmin
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivityAdministrator: AppCompatActivity()
{
    private val loginViewModel by viewModels<LoginViewModelAdmin>()
    private lateinit var binding: ActivityLoginAdministratorBinding
    @Inject
    lateinit var localStore: TempDataTwo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAdministratorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Help.setToolbar(0,supportActionBar,getString(R.string.login_administrator))
        binding.apply {
            btnLoginLecturer.setOnClickListener {
                validateUsernameAndPassword()
            }
            btnPasswordTwo.setOnClickListener {
                getPasswordTwoDigit()
            }
            showBtnEnter()
        }
    }

    private fun showBtnEnter(){
        val visibilityPasswordTwo = binding.tillPasswordTwo.visibility == View.VISIBLE
        binding.btnLoginLecturer.visibility = if (visibilityPasswordTwo) View.VISIBLE else View.GONE
    }

    private fun getPasswordTwoDigit(){
        with(binding){
            val username = tilUsername.editText?.text?.trim().toString()
            val passwordOne = tilPassword.editText?.text?.trim().toString()
            if(username.isEmpty() && passwordOne.isEmpty()){
                Help.showToast(this@LoginActivityAdministrator,"Maaf username dan password tidak boleh kosong")
            }else{
                loginViewModel.getPasswordTwoDigitAdmin(username,passwordOne).observe(this@LoginActivityAdministrator)
                {
                    when(it){
                        is States.Loading -> showProgressBar(true)
                        is States.Success -> {
                            showProgressBar(false)
                            tillPasswordTwo.editText?.setText(it.data)
                        }
                        is States.Failed -> {
                            Help.showToast(this@LoginActivityAdministrator,"Terjadi kesalahan pada server")
                            showProgressBar(false)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun validateUsernameAndPassword(){
        binding.apply {
            val username = tilUsername.editText?.text?.trim().toString()
            val passwordOne = tilPassword.editText?.text?.trim().toString()
            val passwordTwo = tillPasswordTwo.editText?.text?.trim().toString()
            if (username.isEmpty() && passwordOne.isEmpty() && passwordTwo.isEmpty()){
                Help.showToast(this@LoginActivityAdministrator,
                "username dan password pertama dan kedua tidak boleh kosong")
            }else{
                loginViewModel.loginAdmin(username,passwordOne,passwordTwo).observe(this@LoginActivityAdministrator)
                {
                    when(it){
                        is States.Loading -> showProgressBar(true)
                        is States.Success -> {
                            Help.showToast(this@LoginActivityAdministrator,"Anda berhasil masuk")
                            saveTokenAdministrator(it.data.token).also {toIndexAdminActivity()}
                        }
                        is States.Failed -> {
                            showProgressBar(false)
                            Help.showToast(this@LoginActivityAdministrator,"Maaf terjadi kesalahan pada server")
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun toIndexAdminActivity(){
        startActivity(Intent(this@LoginActivityAdministrator,IndexAdministratorActivity::class.java)).also { finish() }
    }

    private fun showProgressBar(isShow: Boolean){
        with(binding){
            progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
            btnLoginLecturer.visibility = if (isShow) View.GONE else View.VISIBLE
            btnPasswordTwo.visibility = if (isShow) View.GONE else View.VISIBLE
        }
    }

    private fun saveTokenAdministrator(token: String,){
        lifecycleScope.launch {
            localStore.putToken(GetTokenLecturer(token = token,Constant.ADMINISTRATOR,"",5,""))
        }
    }
}