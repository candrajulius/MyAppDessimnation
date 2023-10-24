package com.candra.mydesiminationapp.data.source.remote.response.student.index


import com.google.gson.annotations.SerializedName

data class Lecturer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)