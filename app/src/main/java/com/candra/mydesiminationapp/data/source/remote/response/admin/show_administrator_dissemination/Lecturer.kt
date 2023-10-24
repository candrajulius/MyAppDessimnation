package com.candra.mydesiminationapp.data.source.remote.response.admin.show_administrator_dissemination


import com.google.gson.annotations.SerializedName

data class Lecturer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)