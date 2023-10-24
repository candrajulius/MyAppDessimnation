package com.candra.mydesiminationapp.data.source.remote.response.admin.show_administrator_dissemination


import com.candra.mydesiminationapp.data.source.remote.response.admin.show_administrator_dissemination.document_research.Journal
import com.candra.mydesiminationapp.data.source.remote.response.admin.show_administrator_dissemination.document_research.Proceedings
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.show.Member
import com.google.gson.annotations.SerializedName

data class ResponseShowDisseminationAdministrator(
    @SerializedName("abstract")
    val `abstract`: String,
    @SerializedName("administration")
    val administration: Administration,
    @SerializedName("article")
    val article: Article,
    @SerializedName("document")
    val document: Document,
    @SerializedName("id")
    val id: Int,
    @SerializedName("implementation")
    val implementation: Implementation,
    @SerializedName("journal")
    val journal: Journal,
    @SerializedName("keywords")
    val keywords: String,
    @SerializedName("lecturer")
    val lecturer: Lecturer,
    @SerializedName("letter_number")
    val letterNumber: String,
    @SerializedName("members")
    val members: List<Member>,
    @SerializedName("presentation")
    val presentation: Presentation,
    @SerializedName("prosiding")
    val prosiding: Proceedings,
    @SerializedName("schedule")
    val schedule: Schedule,
    @SerializedName("status_caption")
    val statusCaption: String,
    @SerializedName("title")
    val title: String
)