package com.candra.mydesiminationapp.data.source.remote.response.admin.show_administrator_dissemination.document_research

import com.google.gson.annotations.SerializedName

data class Proceedings(
    @SerializedName("conference_grade_caption")
    val conference_grad_caption: String,
    @SerializedName("publisher_name")
    val publisherName:String,
    @SerializedName("publisher_number")
    val publisherNumber: String,
    @SerializedName("article_url")
    val articleUrl: String,
    @SerializedName("publisher_url")
    val publisherUrl: String,
    @SerializedName("conference_location")
    val conference_location: String,
    @SerializedName("conference_start_date")
    val conference_start_date: String,
    @SerializedName("conference_end_date")
    val conference_end_date: String,
)