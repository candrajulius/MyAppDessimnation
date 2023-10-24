package com.candra.mydesiminationapp.data.source.remote.response.research_desimination.create


import com.google.gson.annotations.SerializedName

data class CreateDesiminationLecturer(
    @SerializedName("abstract")
    val `abstract`: Any,
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
    val journal: Any,
    @SerializedName("keywords")
    val keywords: Any,
    @SerializedName("lecturer")
    val lecturer: Lecturer,
    @SerializedName("letter_number")
    val letterNumber: Any,
    @SerializedName("members")
    val members: List<Member>,
    @SerializedName("presentation")
    val presentation: Presentation,
    @SerializedName("prosiding")
    val prosiding: Any,
    @SerializedName("schedule")
    val schedule: Schedule,
    @SerializedName("status_caption")
    val statusCaption: String,
    @SerializedName("title")
    val title: Any
)