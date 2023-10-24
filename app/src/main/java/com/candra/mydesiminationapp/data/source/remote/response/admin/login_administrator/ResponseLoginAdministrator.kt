package com.candra.mydesiminationapp.data.source.remote.response.admin.login_administrator


import com.google.gson.annotations.SerializedName

data class ResponseLoginAdministrator(
    @SerializedName("administrator")
    val administrator: Administrator,
    @SerializedName("token")
    val token: String
)