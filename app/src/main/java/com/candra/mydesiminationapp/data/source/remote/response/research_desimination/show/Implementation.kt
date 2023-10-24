package com.candra.mydesiminationapp.data.source.remote.response.research_desimination.show


import com.google.gson.annotations.SerializedName

data class Implementation(
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("minutes")
    val minutes: String,
    @SerializedName("presence")
    val presence: List<Presence>
)