package com.candra.mydesiminationapp.helper.viewmodel.administrator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.candra.mydesiminationapp.repository.administrator.session.LoginRepositoryAdministrator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModelAdmin @Inject constructor(
    private val loginRepositoryAdministrator: LoginRepositoryAdministrator
): ViewModel()
{
    fun loginAdmin(username: String,password_one: String,password_two: String) = loginRepositoryAdministrator.loginAdmin(
        username,password_one,password_two
    ).asLiveData()

    fun getPasswordTwoDigitAdmin(email: String,password: String) = loginRepositoryAdministrator.getPasswordTwoDigitAdmin(email,password).asLiveData()

}