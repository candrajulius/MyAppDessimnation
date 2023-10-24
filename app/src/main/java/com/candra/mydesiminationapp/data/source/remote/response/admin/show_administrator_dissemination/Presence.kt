package com.candra.mydesiminationapp.data.source.remote.response.admin.show_administrator_dissemination


import com.google.gson.annotations.SerializedName

data class Presence(
    @SerializedName("status")
    val status: String,
    @SerializedName("total")
    val total: Int
)