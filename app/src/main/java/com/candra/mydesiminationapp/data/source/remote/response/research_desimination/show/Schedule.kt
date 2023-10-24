package com.candra.mydesiminationapp.data.source.remote.response.research_desimination.show


import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("class_room_id")
    val classRoomId: Int,
    @SerializedName("end_time")
    val endTime: String,
    @SerializedName("quota")
    val quota: String,
    @SerializedName("start_time")
    val startTime: String
)