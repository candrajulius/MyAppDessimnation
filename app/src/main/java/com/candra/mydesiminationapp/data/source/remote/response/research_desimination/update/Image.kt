package com.candra.mydesiminationapp.data.source.remote.response.research_desimination.update

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("filename")
    val filename: String,
    @SerializedName("url")
    val url: String
)