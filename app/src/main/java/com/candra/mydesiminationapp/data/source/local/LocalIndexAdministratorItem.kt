package com.candra.mydesiminationapp.data.source.local

import android.os.Parcelable
import com.candra.mydesiminationapp.data.source.remote.response.admin.index_dissemination_administrator.Data
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocalIndexAdministratorItem(
    var id: Int,
    var lectruer: String,
    var title: String,
    var status_caption: String,
    var student: StudentMemberAdministrator
): Parcelable

@Parcelize
data class StudentMemberAdministrator(
    var code: String,
    var name: String,
): Parcelable

fun List<Data>.toGenereteListIndexResearchDisseminationItem(): MutableList<LocalIndexAdministratorItem>
{
    val listIndexAdministrationItem = mutableListOf<LocalIndexAdministratorItem>()
    this.forEach { listIndexAdministrationItem.add(it.toIndexAdministrationItem()) }
    return listIndexAdministrationItem
}

fun Data.toIndexAdministrationItem(): LocalIndexAdministratorItem{
    return LocalIndexAdministratorItem(
        id = this.id?: 0,
        lectruer = this.lecturer?: "?",
        title = this.title?: "?",
        status_caption = this.statusCaption?: "?",
        student = StudentMemberAdministrator(
            code = this.student.code,
            name = this.student.name
        )
    )
}