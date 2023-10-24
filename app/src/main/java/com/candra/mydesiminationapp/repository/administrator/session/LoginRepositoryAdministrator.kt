package com.candra.mydesiminationapp.repository.administrator.session

import com.candra.mydesiminationapp.data.source.remote.network.RemoteDataSource
import javax.inject.Inject


class LoginRepositoryAdministrator @Inject constructor(
    private val remoteDataSource: RemoteDataSource
){
    fun loginAdmin(username: String,password_one: String,password_two: String) = remoteDataSource.loginAdministrator(username,password_one,password_two)
    fun getPasswordTwoDigitAdmin(email: String,password: String) = remoteDataSource.getPasswordTwoDigitAdministrator(email,password)
}