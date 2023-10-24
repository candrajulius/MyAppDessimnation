package com.candra.mydesiminationapp.data.source.remote.response.research_desimination.update

import com.google.gson.annotations.SerializedName

data class Presence(
    @SerializedName("status")
    val status: String,
    @SerializedName("total")
    val total: Int
)