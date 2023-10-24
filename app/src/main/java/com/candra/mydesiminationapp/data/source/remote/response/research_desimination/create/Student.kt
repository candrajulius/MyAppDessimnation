package com.candra.mydesiminationapp.data.source.remote.response.research_desimination.create


import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: Status
)