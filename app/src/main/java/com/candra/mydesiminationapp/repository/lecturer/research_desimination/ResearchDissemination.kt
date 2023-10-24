package com.candra.mydesiminationapp.repository.lecturer.research_desimination

import com.candra.mydesiminationapp.data.source.remote.network.RemoteDataSource
import javax.inject.Inject

class ResearchDissemination @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {

    fun showLecturerDissemination(id: Int,token: String) = remoteDataSource.showLecturer(id,token)

    fun getLogDissemination(id: Int,token: String) = remoteDataSource.getLogLecturerItem(id,token)

    fun getIndexDesiminationLecturer(token: String) = remoteDataSource.getIndexLecturerItem(token)

    fun changeStatusToDraftRegister(id: Int,token:String) = remoteDataSource.changeStatusDisseminationToRegister(id,token)

    fun getQueryStudent(query: String,token: String) = remoteDataSource.findLeaderDissemination(query,token)

    fun createDissemination(student_id: Int,token: String) = remoteDataSource.createDissemination(student_id,token)

    fun changeBackStatusNew(id: Int,token: String) = remoteDataSource.changeStatusBackToNew(id,token)

    fun getParticipants(id: Int,token: String) = remoteDataSource.getParticipants(id,token)

    fun getImage(id: Int,token: String) = remoteDataSource.getImage(id,token)

    fun getMember(id: Int,token: String) = remoteDataSource.getMemberStudent(id,token)

    fun changeStatusLecturer(token: String,researchDisseminationId: Int,statusId: Int,note: String) =
        remoteDataSource.changeStatusLecturer(token,researchDisseminationId,statusId,note)

    
}