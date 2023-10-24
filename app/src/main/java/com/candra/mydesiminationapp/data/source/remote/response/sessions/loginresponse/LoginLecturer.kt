package com.candra.mydesiminationapp.data.source.remote.response.sessions.loginresponse


import com.google.gson.annotations.SerializedName

data class LoginLecturer(
    @SerializedName("lecturer")
    val lecturer: Lecturer,
    @SerializedName("token")
    val token: String
)