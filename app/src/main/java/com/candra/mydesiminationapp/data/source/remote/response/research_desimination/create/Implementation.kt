package com.candra.mydesiminationapp.data.source.remote.response.research_desimination.create


import com.google.gson.annotations.SerializedName

data class Implementation(
    @SerializedName("images")
    val images: List<Any>,
    @SerializedName("minutes")
    val minutes: Any,
    @SerializedName("presence")
    val presence: List<Any>
)