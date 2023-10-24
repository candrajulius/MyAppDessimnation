package com.candra.mydesiminationapp.helper.viewmodel.administrator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.candra.mydesiminationapp.repository.administrator.research_dissemination.ResearchDisseminationAdmin
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResearchDisseminationAdminViewModel @Inject constructor(
    private val repositoryAdministrator: ResearchDisseminationAdmin
): ViewModel()
{
    fun showResearchDisseminationAdministrator(token:String,id: Int) =
        repositoryAdministrator.showResearchDisseminationAdministrator(token,id).asLiveData()

    fun getIndexResearchDisseminationAdministrator(token: String) =
        repositoryAdministrator.getIndexResearchDisseminationAdministrator(token).asLiveData()

    fun getLogResearchDisseminationAdministrator(token: String,id: Int) =
        repositoryAdministrator.getLogResearchDisseminationAdministrator(token,id).asLiveData()

    fun getParticipantsResearchDisseminationAdministrator(token: String,id: Int) =
        repositoryAdministrator.getParticipantsResearchDisseminationAdministrator(token,id).asLiveData()

    fun getImageResearchDisseminationAdministrator(token: String,id: Int) =
        repositoryAdministrator.getImageResearchDisseminationAdministrator(token,id).asLiveData()

    fun postScheduleResearchDisseminationAdministrator(token: String,id:Int,class_room_id: String,start_time: String,
    end_time: String,participants_size: Int) = repositoryAdministrator.postScheduleResearchDisseminationAdministrator(
        token,id,class_room_id,start_time,end_time,participants_size
    ).asLiveData()

    fun getListClassResearchDisseminationAdministrator(token: String,id: Int) =
        repositoryAdministrator.getListClassResearchDisseminationAdministrator(token,id).asLiveData()

    fun changeStatusResearchDisseminationAdministrator(token: String,research_dissemination_id: Int,
    status_id: Int,note: String) = repositoryAdministrator.changeStatusResearchDisseminationAdministrator(
        token,research_dissemination_id,status_id,note
    ).asLiveData()
}