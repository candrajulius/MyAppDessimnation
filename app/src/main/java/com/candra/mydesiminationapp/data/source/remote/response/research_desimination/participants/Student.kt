package com.candra.mydesiminationapp.data.source.remote.response.research_desimination.participants


import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String
)