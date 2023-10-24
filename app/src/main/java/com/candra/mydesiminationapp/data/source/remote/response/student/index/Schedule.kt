package com.candra.mydesiminationapp.data.source.remote.response.student.index


import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("class_room_id")
    val classRoomId: Any,
    @SerializedName("end_time")
    val endTime: Any,
    @SerializedName("quota")
    val quota: String,
    @SerializedName("start_time")
    val startTime: Any
)