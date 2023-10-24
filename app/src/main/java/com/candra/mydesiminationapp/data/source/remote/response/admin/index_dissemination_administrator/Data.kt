package com.candra.mydesiminationapp.data.source.remote.response.admin.index_dissemination_administrator


import com.candra.mydesiminationapp.data.source.remote.response.student.index.Student
import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: Int,
    @SerializedName("lecturer")
    val lecturer: String,
    @SerializedName("status_caption")
    val statusCaption: String,
    @SerializedName("student")
    val student: Student,
    @SerializedName("title")
    val title: String
)