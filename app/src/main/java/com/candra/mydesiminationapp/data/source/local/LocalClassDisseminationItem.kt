package com.candra.mydesiminationapp.data.source.local

import android.os.Parcelable
import com.candra.mydesiminationapp.data.source.remote.response.admin.list_class_dissemination_administrator.ResponseListResearchDisseminationAdministratorItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocalClassDisseminationItem(
    var id: Int,
    var title: String
):Parcelable

fun List<ResponseListResearchDisseminationAdministratorItem>.toGenerateClassListItem(): MutableList<LocalClassDisseminationItem>
{
    val listClassDesseminationItem = mutableListOf<LocalClassDisseminationItem>()
    this.forEach { listClassDesseminationItem.add(it.toClassResearchDisseminationItem()) }
    return listClassDesseminationItem
}

fun ResponseListResearchDisseminationAdministratorItem.toClassResearchDisseminationItem(): LocalClassDisseminationItem{
    return LocalClassDisseminationItem(
        id = this.id?: 0,
        title = this.title?: "?"
    )
}