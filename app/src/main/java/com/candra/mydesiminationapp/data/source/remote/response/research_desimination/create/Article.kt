package com.candra.mydesiminationapp.data.source.remote.response.research_desimination.create


import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("filename")
    val filename: Any,
    @SerializedName("url")
    val url: String
)