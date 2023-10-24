package com.candra.mydesiminationapp.data.source.remote.response.admin.log_dissemination_administrator


import com.google.gson.annotations.SerializedName

data class ResponseLogResearchDisseminationItem(
    @SerializedName("administrator_id")
    val administratorId: Int,
    @SerializedName("by_name")
    val byName: String,
    @SerializedName("created_at_caption")
    val createdAtCaption: String,
    @SerializedName("lecturer_id")
    val lecturerId: Int,
    @SerializedName("note")
    val note: String,
    @SerializedName("status_caption")
    val statusCaption: String,
    @SerializedName("student_id")
    val studentId: Int
)