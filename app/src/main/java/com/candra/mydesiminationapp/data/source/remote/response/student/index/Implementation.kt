package com.candra.mydesiminationapp.data.source.remote.response.student.index


import com.google.gson.annotations.SerializedName

data class Implementation(
    @SerializedName("images")
    val images: List<Any>,
    @SerializedName("minutes")
    val minutes: String,
    @SerializedName("presence")
    val presence: List<Any>
)