package com.candra.mydesiminationapp.data.source.remote.response.sessions.LoginStudentResponse


import com.google.gson.annotations.SerializedName

data class LoginStudentResponse(
    @SerializedName("student")
    val student: Student,
    @SerializedName("token")
    val token: String
)