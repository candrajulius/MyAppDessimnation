package com.candra.mydesiminationapp.data.source.remote.response.research_desimination.create


import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("as")
    val asX: String,
    @SerializedName("id")
    val id: Any,
    @SerializedName("student")
    val student: Student
)