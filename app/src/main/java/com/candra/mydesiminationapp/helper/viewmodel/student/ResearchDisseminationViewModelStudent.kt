package com.candra.mydesiminationapp.helper.viewmodel.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.candra.mydesiminationapp.repository.student.research_desimination.ResearchDisseminationStudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ResearchDisseminationViewModelStudent @Inject constructor(
    private val researchDissemination: ResearchDisseminationStudentRepository
): ViewModel()
{
    fun indexStudent(token:String) = researchDissemination.getShowDisseminationStudent(token).asLiveData()

    fun backToDraftRegister(token: String,id: Int) = researchDissemination.backToDraftRegisterStudent(token,id).asLiveData()

    fun acceptMemberToDissemination(token: String,id: Int) = researchDissemination.acceptMemberToDissemination(token,id).asLiveData()

    fun logStudent(token: String,id: Int) = researchDissemination.logStudent(token,id).asLiveData()

    fun correctionMember(token: String,positionId: Int,studentCode: String,statusId: Int,id: Int) =
        researchDissemination.correctionMember(token,positionId,studentCode,statusId,id).asLiveData()

    fun rejectMemberDissemination(token: String,id: Int) = researchDissemination.rejectMemberDissemination(token,id).asLiveData()

    fun addMemberDissemination(token: String,researchDisseminationId: Int,positionId: Int,studentCode: String) =
        researchDissemination.addMemberDissemination(token,researchDisseminationId,positionId,studentCode).asLiveData()

    fun updateDissemination(token: String, id: Int, title: RequestBody, abstract: RequestBody, keywords: RequestBody,
                            publisherId: RequestBody, publisherName: RequestBody, publisherNumber: RequestBody, publisherUrl: RequestBody,articleUrl: RequestBody, journalGradeId: RequestBody,
                            journalYear: RequestBody, journalVolume: RequestBody, journalNumber: RequestBody,article: MultipartBody.Part, presentation: MultipartBody.Part, administration: MultipartBody.Part)
    = researchDissemination.updateDisseminationStudent(token,id,title,abstract,keywords,publisherId,publisherName,publisherNumber,publisherUrl,articleUrl,
        journalGradeId,journalYear,journalVolume,journalNumber,
        article,presentation,administration)

    fun changeStatusStudent(token: String,researchDisseminationId: Int,statusId: Int,note: String) =
        researchDissemination.changeStatusStudent(token,researchDisseminationId,statusId,note).asLiveData()
}