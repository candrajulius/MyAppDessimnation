package com.candra.mydesiminationapp.repository.administrator.research_dissemination

import com.candra.mydesiminationapp.data.source.remote.network.RemoteDataSource
import javax.inject.Inject

class ResearchDisseminationAdmin @Inject constructor(
    private val remoteDataSource: RemoteDataSource
){
    fun showResearchDisseminationAdministrator(token: String,id: Int) =
        remoteDataSource.showResearchDisseminationAdministrator(token,id)

    fun getIndexResearchDisseminationAdministrator(token: String) = remoteDataSource.getIndexResearchDisseminationAdministrator(token)

    fun getLogResearchDisseminationAdministrator(token: String,id:Int) = remoteDataSource.getLogResearchDisseminationAdministrator(token,id)

    fun getParticipantsResearchDisseminationAdministrator(token: String,id:Int) = remoteDataSource.getParticipantsResearchDisseminationAdmin(token,id)

    fun getImageResearchDisseminationAdministrator(token: String,id: Int) = remoteDataSource.getImageResearchDisseminationAdminItem(token,id)

    fun postScheduleResearchDisseminationAdministrator(token: String,id: Int,class_room_id: String,start_time: String,end_time: String,participant_size: Int) =
        remoteDataSource.postScheduleResearchDisseminationAdmin(token,id,class_room_id,start_time,end_time,participant_size)

    fun getListClassResearchDisseminationAdministrator(token: String,id: Int) = remoteDataSource.getListClassResearchDisseminationAdmin(token,id)

    fun changeStatusResearchDisseminationAdministrator(token: String,research_dissemination_id: Int,status_id: Int,note: String) =
        remoteDataSource.changeStatusResearchDisseminationAdminItem(token,research_dissemination_id,status_id,note)

}