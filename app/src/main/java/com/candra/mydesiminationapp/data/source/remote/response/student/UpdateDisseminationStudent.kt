package com.candra.mydesiminationapp.data.source.remote.response.student


import com.google.gson.annotations.SerializedName

data class UpdateDisseminationStudent(
    @SerializedName("message")
    val message: String
)