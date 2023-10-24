package com.candra.mydesiminationapp.data.source.remote.response.research_desimination.participants


import com.google.gson.annotations.SerializedName

data class ParticipantsDesiminationLecturerItem(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("status_caption")
    val statusCaption: String,
    @SerializedName("student")
    val student: Student,
    @SerializedName("updated_at")
    val updatedAt: String
)