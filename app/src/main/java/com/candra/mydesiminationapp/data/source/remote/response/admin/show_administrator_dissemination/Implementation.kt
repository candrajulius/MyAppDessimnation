package com.candra.mydesiminationapp.data.source.remote.response.admin.show_administrator_dissemination


import com.google.gson.annotations.SerializedName

data class Implementation(
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("minutes")
    val minutes: String,
    @SerializedName("presence")
    val presence: List<Presence>
)