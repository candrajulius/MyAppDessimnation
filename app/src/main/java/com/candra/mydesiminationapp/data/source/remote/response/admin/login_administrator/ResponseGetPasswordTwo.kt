package com.candra.mydesiminationapp.data.source.remote.response.admin.login_administrator


import com.google.gson.annotations.SerializedName

data class ResponseGetPasswordTwo(
    @SerializedName("digit")
    val digit: String
)