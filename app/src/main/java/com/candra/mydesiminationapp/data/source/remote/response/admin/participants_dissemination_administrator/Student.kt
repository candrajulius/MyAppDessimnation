package com.candra.mydesiminationapp.data.source.remote.response.admin.participants_dissemination_administrator


import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String
)