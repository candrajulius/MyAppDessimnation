package com.candra.mydesiminationapp.data.source.remote.response.student.index


import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("as")
    val asX: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("student")
    val student: Student
)