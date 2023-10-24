package com.candra.mydesiminationapp.data.source.remote.response.admin.list_class_dissemination_administrator


import com.google.gson.annotations.SerializedName

data class ResponseListResearchDisseminationAdministratorItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
)