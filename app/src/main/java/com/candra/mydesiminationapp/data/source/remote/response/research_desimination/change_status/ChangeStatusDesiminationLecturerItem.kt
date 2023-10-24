package com.candra.mydesiminationapp.data.source.remote.response.research_desimination.change_status


import com.google.gson.annotations.SerializedName

data class ChangeStatusDesiminationLecturerItem(
    @SerializedName("head_name")
    val headName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("status_caption")
    val statusCaption: String,
    @SerializedName("title")
    val title: String
)