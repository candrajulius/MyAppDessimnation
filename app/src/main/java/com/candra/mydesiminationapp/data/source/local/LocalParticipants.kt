package com.candra.mydesiminationapp.data.source.local

import android.os.Parcelable
import com.candra.mydesiminationapp.data.source.remote.response.admin.participants_dissemination_administrator.ResponseParticipantsAdministratorItem
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.participants.ParticipantsDesiminationLecturerItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocalParticipants(
    var createAt: String,
    var statusCaption: String,
    var student: Student,
    var updateAt: String
): Parcelable

@Parcelize
data class Student(
    var code: String,
    var name: String
): Parcelable

fun List<ParticipantsDesiminationLecturerItem>.toGenerateParticipantsItem(): MutableList<LocalParticipants>
{
    val listParticipantsLecturerItem = mutableListOf<LocalParticipants>()
    this.forEach { listParticipantsLecturerItem.add(it.toParticipantsIndexItem()) }
    return listParticipantsLecturerItem
}

fun ParticipantsDesiminationLecturerItem.toParticipantsIndexItem(): LocalParticipants{
    return  LocalParticipants(
        createAt = this.createdAt?: "",
        statusCaption = this.statusCaption?: "",
        student = Student(code = this.student.code?: "", name = this.student.name?: ""),
        updateAt = this.updatedAt?: ""
    )
}

fun List<ResponseParticipantsAdministratorItem>.toGenerateParticipantsAdminItem(): MutableList<LocalParticipants>
{
    val listParticipantsAdminItem = mutableListOf<LocalParticipants>()
    this.forEach { listParticipantsAdminItem.add(it.toParticipantsAdminItem()) }
    return listParticipantsAdminItem
}

fun ResponseParticipantsAdministratorItem.toParticipantsAdminItem(): LocalParticipants{
    return LocalParticipants(
        createAt = this.createdAt?: "?",
        statusCaption = this.statusCaption?: "?",
        student = Student(code = this.student.code?: "?", name = this.student.name),
        updateAt = this.updatedAt?: "?"
    )
}
