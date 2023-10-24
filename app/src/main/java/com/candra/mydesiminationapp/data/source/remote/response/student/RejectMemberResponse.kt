package com.candra.mydesiminationapp.data.source.remote.response.student


import com.google.gson.annotations.SerializedName

data class RejectMemberResponse(
    @SerializedName("message")
    val message: String
)