package com.candra.mydesiminationapp.repository.lecturer.session

import com.candra.mydesiminationapp.data.source.remote.network.ApiInterface
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiInterface: ApiInterface
){
    suspend fun loginLecturer(email: String,password: String) = apiInterface.loginLecturer(email,password)
}