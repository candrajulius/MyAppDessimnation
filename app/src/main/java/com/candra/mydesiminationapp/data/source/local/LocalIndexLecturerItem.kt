package com.candra.mydesiminationapp.data.source.local

import android.os.Parcelable
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.index.IndexDesiminationLecturerItem
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.index.IndexDisseminationResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocalIndexLecturerItem(
    var headName: String,
    var id: Int,
    var statusCaption: String,
    var title: String
): Parcelable

fun List<IndexDesiminationLecturerItem>.toGenerateListLecturerItem(): MutableList<LocalIndexLecturerItem>
{
    val listIndexLecturerItem = mutableListOf<LocalIndexLecturerItem>()
    this.forEach{listIndexLecturerItem.add(it.toIndexLecturerItem())}
    return listIndexLecturerItem
}

fun IndexDesiminationLecturerItem.toIndexLecturerItem(): LocalIndexLecturerItem{
    return LocalIndexLecturerItem(
        headName = this.headName?:"?",
        id = this.id?:0,
        statusCaption = this.statusCaption?: "?",
        title = this.title?: "?"
    )
}