package com.candra.mydesiminationapp.data.source.remote.response.research_desimination.update


import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("as")
    val asX: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("student")
    val student: Student
)