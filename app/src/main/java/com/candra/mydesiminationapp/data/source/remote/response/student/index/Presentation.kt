package com.candra.mydesiminationapp.data.source.remote.response.student.index


import com.google.gson.annotations.SerializedName

data class Presentation(
    @SerializedName("filename")
    val filename: String,
    @SerializedName("url")
    val url: String
)