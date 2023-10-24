package com.candra.mydesiminationapp.helper.viewmodel.Lecturer

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.candra.mydesiminationapp.R
import com.candra.mydesiminationapp.data.source.remote.network.GetTokenLecturer
import com.candra.mydesiminationapp.helper.Constant
import com.candra.mydesiminationapp.helper.TempDataTwo
import com.candra.mydesiminationapp.repository.lecturer.session.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KFunction1

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val temptData: TempDataTwo
): ViewModel()
{
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _messageError = MutableLiveData<String>()
    val messageError:LiveData<String> = _messageError

    private val _messageSuccess = MutableLiveData<String>()
    val messageSuccess: LiveData<String> = _messageSuccess

    suspend fun loginLecturerDesimination(
        context: Context,
        email: String,
        password: String,
    ) = viewModelScope.launch {
        _loading.value = true
        val responseLoginData = loginRepository.loginLecturer(email,password)
        try {
            responseLoginData.let {
                if (it.isSuccessful && it.body() != null){
                    _loading.value = false
                    _success.value = true
                    val resultToken = it.body()!!.token
                    _messageSuccess.value = context.getString(R.string.login_success)
                    temptData.putToken(GetTokenLecturer(resultToken,Constant.LECTURER,"",5,""))
                }else{
                    Log.d("TAG", "loginLecturerDesimination: ${it.code()}")
                    _loading.value = false
                    _success.value = false
                    _messageError.value = "${it.code()} => ${it.errorBody().toString()}"
                }
            }
        }catch (e: Exception){
            _loading.value = false
            _success.value = false
            _messageError.value = e.message.toString()
            Log.d("TAG", "loginLecturerDesimination: ${e.message.toString()}")
        }
    }
}