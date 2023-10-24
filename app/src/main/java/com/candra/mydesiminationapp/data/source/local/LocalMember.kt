package com.candra.mydesiminationapp.data.source.local

import android.os.Parcelable
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.show.Member
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocalMember(
    var asX: String,
    var id: Int,
    var student: StudentMember
): Parcelable

@Parcelize
data class StudentMember(
    var code: String,
    var name: String,
    var status:Status
): Parcelable

@Parcelize
data class Status(
    var caption: String,
    var id: Int
): Parcelable


fun List<Member>.toGenerateMember(): MutableList<LocalMember>
{
    val listMemberIndex = mutableListOf<LocalMember>()
    this.forEach { listMemberIndex.add(it.toMember()) }
    return listMemberIndex
}

fun List<com.candra.mydesiminationapp.data.source.remote.response.student.index.Member>.toGenerateMemberStudent(): MutableList<LocalMember>
{
    val listMemberIndex = mutableListOf<LocalMember>()
    this.forEach { listMemberIndex.add(it.toMemberStudent()) }
    return listMemberIndex
}

fun com.candra.mydesiminationapp.data.source.remote.response.student.index.Member.toMemberStudent(): LocalMember{
    return LocalMember(
        asX = this.asX?: "?",
        id = this.id?: 0,
        student = StudentMember(
            code = this.student.code?: "?",
            name = this.student.name?: "?",
            status = Status(
                caption = this.student.status.caption?: "?",
                id = this.student.status.id?: 0
            )
        )
    )
}

fun Member.toMember(): LocalMember{
    return LocalMember(
        asX = this.asX?: "?",
        id = this.id?: 0,
        student = StudentMember(
            code = this.student.code?: "?",
            name = this.student.name?: "?",
            status = Status(
                caption = this.student.status.caption?: "?",
                id = this.student.status.id?: 0
            )
        )
    )
}