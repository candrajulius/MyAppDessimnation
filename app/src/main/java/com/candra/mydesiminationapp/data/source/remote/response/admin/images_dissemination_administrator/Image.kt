package com.candra.mydesiminationapp.data.source.remote.response.admin.images_dissemination_administrator


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("filename")
    val filename: String,
    @SerializedName("url")
    val url: String
)