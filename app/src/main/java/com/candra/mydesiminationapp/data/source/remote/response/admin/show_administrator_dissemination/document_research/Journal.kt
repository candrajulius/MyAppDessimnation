package com.candra.mydesiminationapp.data.source.remote.response.admin.show_administrator_dissemination.document_research


import com.google.gson.annotations.SerializedName

data class Journal(
    @SerializedName("article_url")
    val articleUrl: String,
    @SerializedName("journal_grade_caption")
    val journalGradeCaption: String,
    @SerializedName("journal_number")
    val journalNumber: Int,
    @SerializedName("journal_volume")
    val journalVolume: Int,
    @SerializedName("journal_year")
    val journalYear: Int,
    @SerializedName("publisher_name")
    val publisherName: String,
    @SerializedName("publisher_number")
    val publisherNumber: String,
    @SerializedName("publisher_url")
    val publisherUrl: String
)