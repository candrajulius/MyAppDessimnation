package com.candra.mydesiminationapp.data.source.remote.response.admin.participants_dissemination_administrator


import com.google.gson.annotations.SerializedName

data class ResponseParticipantsAdministratorItem(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("status_caption")
    val statusCaption: String,
    @SerializedName("student")
    val student: Student,
    @SerializedName("updated_at")
    val updatedAt: String
)