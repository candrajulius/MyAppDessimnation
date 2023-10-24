package com.candra.mydesiminationapp.helper.viewmodel.student


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.candra.mydesiminationapp.repository.student.session.LoginRepositoryStudent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModelStudent @Inject constructor(
    private val loginRepository: LoginRepositoryStudent,
): ViewModel()
{
    fun loginStudent(code: String,password: String,tokenFcm: String) = loginRepository.loginStudent(code,password,tokenFcm).asLiveData()
}