package com.candra.mydesiminationapp.data.source.remote.response.research_desimination.image


import com.google.gson.annotations.SerializedName

data class ImageDesiminationLecturerItem(
    @SerializedName("image")
    val image: Image,
    @SerializedName("note")
    val note: String
)