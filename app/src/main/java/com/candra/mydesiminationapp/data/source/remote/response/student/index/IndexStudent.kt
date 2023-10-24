package com.candra.mydesiminationapp.data.source.remote.response.student.index


import com.google.gson.annotations.SerializedName

data class IndexStudent(
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
    val prosiding: Any,
    @SerializedName("schedule")
    val schedule: Schedule,
    @SerializedName("status_caption")
    val statusCaption: String,
    @SerializedName("title")
    val title: String
)