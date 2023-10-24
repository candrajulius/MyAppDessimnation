package com.candra.mydesiminationapp.data.source.local

import android.os.Parcelable
import com.candra.mydesiminationapp.data.source.remote.response.admin.log_dissemination_administrator.ResponseLogResearchDisseminationItem
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.log.LogDesiminationLecturerItem
import com.candra.mydesiminationapp.data.source.remote.response.student.log_student.LogStudentItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocalLogDisseminationLecturerItem(
    var administratorId: Int,
    var byName: String,
    var createdAtCaption: String,
    var lecturerId: Int,
    var note: String,
    var statusCaption: String,
    var studentId: Int
): Parcelable

fun List<LogDesiminationLecturerItem>.toGenerateListLogLecturerItem(): MutableList<LocalLogDisseminationLecturerItem> {
    val listLogLecturerItem = mutableListOf<LocalLogDisseminationLecturerItem>()
    this.forEach { listLogLecturerItem.add(it.toLogLecturerItem()) }
    return listLogLecturerItem
}

fun List<LogStudentItem>.toGenerateLocalStudent(): MutableList<LocalLogDisseminationLecturerItem>{
    val listLogStudent = mutableListOf<LocalLogDisseminationLecturerItem>()
    this.forEach { listLogStudent.add(it.toLogStudent()) }
    return listLogStudent
}

fun List<ResponseLogResearchDisseminationItem>.toGenerateLogResearchDisseminationAdmin(): MutableList<LocalLogDisseminationLecturerItem>{
    val listLogAdmin = mutableListOf<LocalLogDisseminationLecturerItem>()
    this.forEach { listLogAdmin.add(it.toLogAdmin()) }
    return listLogAdmin
}

fun ResponseLogResearchDisseminationItem.toLogAdmin(): LocalLogDisseminationLecturerItem{
    return LocalLogDisseminationLecturerItem(
        administratorId = this.administratorId?: 0,
        byName = this.byName?: "?",
        createdAtCaption = this.createdAtCaption?: "?",
        lecturerId = this.lecturerId?: 0,
        note = this.note?: "?",
        statusCaption = this.statusCaption?: "?",
        studentId = this.studentId?: 0
    )
}

fun LogStudentItem.toLogStudent(): LocalLogDisseminationLecturerItem{
    return LocalLogDisseminationLecturerItem(
        administratorId = this.administratorId?: 0,
        byName = this.byName?: "?",
        createdAtCaption = this.createdAtCaption?: "?",
        lecturerId = this.lecturerId?: 0,
        note = this.note?: "?",
        statusCaption = this.note?: "?",
        studentId = this.studentId?: 0
    )
}

fun LogDesiminationLecturerItem.toLogLecturerItem(): LocalLogDisseminationLecturerItem{
    return LocalLogDisseminationLecturerItem(
        administratorId = this.administratorId?: 0,
        byName = this.byName?: "Data is null",
        createdAtCaption = this.createdAtCaption?: "Data is null",
        lecturerId = this.lecturerId?: 0,
        note = this.note?: "Data is null",
        statusCaption = this.statusCaption?: "Data is null",
        studentId = this.studentId?: 0
    )
}
