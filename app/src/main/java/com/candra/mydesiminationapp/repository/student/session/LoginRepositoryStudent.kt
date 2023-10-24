package com.candra.mydesiminationapp.repository.student.session

import com.candra.mydesiminationapp.data.source.remote.network.RemoteDataSource
import javax.inject.Inject

class LoginRepositoryStudent @Inject constructor(
    private val remoteDataSource: RemoteDataSource
){
    fun loginStudent(code: String,password: String,token_fcm: String) = remoteDataSource.loginStudent(code,password, token_fcm)
}