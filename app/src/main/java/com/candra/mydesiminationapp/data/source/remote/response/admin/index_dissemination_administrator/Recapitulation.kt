package com.candra.mydesiminationapp.data.source.remote.response.admin.index_dissemination_administrator


import com.google.gson.annotations.SerializedName

data class Recapitulation(
    @SerializedName("program_id")
    val programId: Int,
    @SerializedName("program_title")
    val programTitle: String,
    @SerializedName("total")
    val total: Int
)