package com.candra.mydesiminationapp.data.source.remote.response.research_desimination.create


import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("caption")
    val caption: String,
    @SerializedName("id")
    val id: Int
)