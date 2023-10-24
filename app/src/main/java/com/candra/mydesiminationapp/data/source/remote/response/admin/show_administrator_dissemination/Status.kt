package com.candra.mydesiminationapp.data.source.remote.response.admin.show_administrator_dissemination


import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("caption")
    val caption: String,
    @SerializedName("id")
    val id: Int
)