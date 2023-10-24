package com.candra.mydesiminationapp.helper.viewmodel.Lecturer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.candra.mydesiminationapp.data.source.local.LocalIndexLecturerItem
import com.candra.mydesiminationapp.data.source.local.LocalLogDisseminationLecturerItem
import com.candra.mydesiminationapp.data.source.remote.network.States
import com.candra.mydesiminationapp.repository.lecturer.research_desimination.ResearchDissemination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResearchDisseminationViewModel @Inject constructor(
    private val researchDissemination: ResearchDissemination
): ViewModel()
{
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getIndexLecturerItem(token: String) = researchDissemination.getIndexDesiminationLecturer(token).asLiveData()

    fun getLogLecturerItem(id: Int,token: String) = researchDissemination.getLogDissemination(id,token).asLiveData()

    fun showLecturerDissemination(id: Int,token: String) = researchDissemination.showLecturerDissemination(id,token).asLiveData()

    fun changeStatusToDraftRegister(id: Int,token: String) = researchDissemination.changeStatusToDraftRegister(id,token).asLiveData()

    fun queryStudent(query: String,token: String) = researchDissemination.getQueryStudent(query,token).asLiveData()

    fun createDissemination(student_id: Int,token: String) = researchDissemination.createDissemination(student_id,token).asLiveData()

    fun backStatusToNew(id: Int,token: String) = researchDissemination.changeBackStatusNew(id,token).asLiveData()

    fun getImage(id: Int,token: String) = researchDissemination.getImage(id,token).asLiveData()

    fun getParticipants(id: Int,token: String) = researchDissemination.getParticipants(id,token).asLiveData()

    fun getMember(id: Int,token: String) = researchDissemination.getMember(id,token).asLiveData()

    fun changeStatusLecturer(token: String,researchDisseminationId: Int,statusId: Int,note: String) =
        researchDissemination.changeStatusLecturer(token,researchDisseminationId,statusId,note)




}