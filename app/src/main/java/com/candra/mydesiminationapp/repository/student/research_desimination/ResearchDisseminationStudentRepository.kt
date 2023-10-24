package com.candra.mydesiminationapp.repository.student.research_desimination

import com.candra.mydesiminationapp.data.source.remote.network.RemoteDataSource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ResearchDisseminationStudentRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
){
    fun getShowDisseminationStudent(token: String) = remoteDataSource.getShowDisseminationStudent(token)

    fun backToDraftRegisterStudent(token: String,id: Int) = remoteDataSource.backToDraftRegisterStudent(token,id)

    fun acceptMemberToDissemination(token: String,id: Int) = remoteDataSource.acceptMemberToDissemination(token,id)

    fun logStudent(token: String,id: Int) = remoteDataSource.logStudent(token,id)

    fun correctionMember(token: String,positionId: Int,studentCode: String,statusId: Int,id: Int) =
        remoteDataSource.correctionMember(token,positionId,studentCode,statusId,id)

    fun rejectMemberDissemination(token: String,id: Int) = remoteDataSource.rejectMemberDissemination(token,id)

    fun addMemberDissemination(token: String,researchDisseminationId: Int,positionId: Int,studentCode: String) =
        remoteDataSource.addMemberDissemination(token,researchDisseminationId,positionId,studentCode)

    fun updateDisseminationStudent(token: String, id: Int, title: RequestBody, abstract: RequestBody, keywords: RequestBody,
                                   publisherId: RequestBody, publisherName: RequestBody, publisherNumber: RequestBody,publisherUrl: RequestBody,articleUrl: RequestBody, journalGradeId: RequestBody,
                                   journalYear: RequestBody, journalVolume: RequestBody, journalNumber: RequestBody, article: MultipartBody.Part, presentation: MultipartBody.Part, administration: MultipartBody.Part
    ) = remoteDataSource.updateDisseminationLecturer(token,id,title,abstract,keywords,publisherId,publisherName,publisherNumber,publisherUrl,articleUrl,
        journalGradeId,journalYear,journalVolume,journalNumber,
        article,presentation,administration)

    fun changeStatusStudent(token: String,researchDisseminationId:Int,statusId: Int,note: String) =
        remoteDataSource.changeStatusStudent(token,researchDisseminationId,statusId,note)
}