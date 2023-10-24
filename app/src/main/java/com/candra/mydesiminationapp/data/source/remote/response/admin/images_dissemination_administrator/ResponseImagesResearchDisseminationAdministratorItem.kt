package com.candra.mydesiminationapp.data.source.remote.response.admin.images_dissemination_administrator


import com.google.gson.annotations.SerializedName

data class ResponseImagesResearchDisseminationAdministratorItem(
    @SerializedName("image")
    val image: Image,
    @SerializedName("note")
    val note: String
)