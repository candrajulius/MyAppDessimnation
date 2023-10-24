package com.candra.mydesiminationapp.data.source.remote.network

import android.util.Log
import com.candra.mydesiminationapp.data.source.local.LocalClassDisseminationItem
import com.candra.mydesiminationapp.data.source.local.LocalImage
import com.candra.mydesiminationapp.data.source.local.LocalIndexAdministratorItem
import com.candra.mydesiminationapp.data.source.local.LocalIndexLecturerItem
import com.candra.mydesiminationapp.data.source.local.LocalLogDisseminationLecturerItem
import com.candra.mydesiminationapp.data.source.local.LocalMember
import com.candra.mydesiminationapp.data.source.local.LocalParticipants
import com.candra.mydesiminationapp.data.source.local.toGenerateClassListItem
import com.candra.mydesiminationapp.data.source.local.toGenerateImageItem
import com.candra.mydesiminationapp.data.source.local.toGenerateListImageItem
import com.candra.mydesiminationapp.data.source.local.toGenerateListLecturerItem
import com.candra.mydesiminationapp.data.source.local.toGenerateListLogLecturerItem
import com.candra.mydesiminationapp.data.source.local.toGenerateLocalStudent
import com.candra.mydesiminationapp.data.source.local.toGenerateLogResearchDisseminationAdmin
import com.candra.mydesiminationapp.data.source.local.toGenerateMember
import com.candra.mydesiminationapp.data.source.local.toGenerateParticipantsAdminItem
import com.candra.mydesiminationapp.data.source.local.toGenerateParticipantsItem
import com.candra.mydesiminationapp.data.source.local.toGenereteListIndexResearchDisseminationItem
import com.candra.mydesiminationapp.data.source.remote.response.admin.change_status_dissemination_administrator.ResponseChangeStatusResearchDisseminationAdministrator
import com.candra.mydesiminationapp.data.source.remote.response.admin.index_dissemination_administrator.ResponseIndexResearchDissemination
import com.candra.mydesiminationapp.data.source.remote.response.admin.log_dissemination_administrator.ResponseLogResearchDissemination
import com.candra.mydesiminationapp.data.source.remote.response.admin.login_administrator.ResponseLoginAdministrator
import com.candra.mydesiminationapp.data.source.remote.response.admin.show_administrator_dissemination.ResponseShowDisseminationAdministrator
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.change_register.ChangeRegisterLecturer
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.change_status.ChangeStatusDesiminationLecturer
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.create.CreateDesiminationLecturer
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.show.ShowLecturerDesimination
import com.candra.mydesiminationapp.data.source.remote.response.sessions.LoginStudentResponse.LoginStudentResponse
import com.candra.mydesiminationapp.data.source.remote.response.student.index.IndexStudent
import com.candra.mydesiminationapp.helper.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiInterface: ApiInterface
){
    companion object{
        const val TAG = "RemoteDataSource"
    }
    fun getIndexLecturerItem(token: String) = flow<States<List<LocalIndexLecturerItem>>> {
        emit(States.loading())
        val dataService = apiInterface.getIndexLecturer(Constant.BAREARER + token)
        dataService.let {
            if (it.isSuccessful && it.body() != null){
                emit(States.success(it.body()?.toGenerateListLecturerItem()?: listOf()))
            }else{
                Log.d(TAG, "getIndexLecturerItem: $it.code()")
                emit(States.failed(it.message().toString()))
            }
        }
    }.catch {
        Log.d(TAG, "getLecturerItem: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getLogLecturerItem(id: Int,token: String) = flow<States<List<LocalLogDisseminationLecturerItem>>> {
        emit(States.loading())
        val dataLogService = apiInterface.getLogsLecturer(Constant.BAREARER + token,id)
        dataLogService.let {
            if (it.isSuccessful && it.body() != null){
                emit(States.success(it.body()?.toGenerateListLogLecturerItem()?: listOf()))
            }else{
                emit(States.failed(it.message().toString()))
            }
        }
    }.catch {
        Log.d(TAG, "getLogLecturerItem: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun showLecturer(id: Int,token: String) = flow<States<ShowLecturerDesimination>> {
        emit(States.loading())
        apiInterface.showLecturer(Constant.BAREARER + token,id).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d("TAG", "showLecturer: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun changeStatusDisseminationToRegister(id: Int,token: String) = flow<States<String>> {
        emit(States.loading())
        val service = apiInterface.changeDraftRegister(Constant.BAREARER + token,id)
        service.let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!.message))
            else emit(States.failed(it.errorBody().toString()))
        }
    }.catch {
        emit(States.failed(it.message.toString()))
        Log.d(TAG, "changeStatusDisseminationToRegister: ${it.message.toString()}")
    }

    fun findLeaderDissemination(queryStudent: String,token: String) = flow<States<String>> {
        emit(States.loading())
        apiInterface.getQueryStudent(Constant.BAREARER + token,queryStudent).let {
            emit(States.success(it.toString()?: ""))
        }
    }.catch {
        emit(States.failed(it.message.toString()))
        Log.d("TAG", "findLeaderDissemination: ${it.message}")
    }

    fun createDissemination(studentId: Int,token: String) = flow<States<CreateDesiminationLecturer>> {
        emit(States.loading())
        val dataService = apiInterface.createDesimination(Constant.BAREARER + token,studentId)
        dataService.let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else {
                Log.d("TAG", "createDissemination: ${it.message().toString()}")
                emit(States.failed(it.errorBody().toString()))
            }
        }
    }.catch {
        emit(States.failed(it.message.toString()))
        Log.d("TAG", "createDessimination: ${it.message.toString()}")
    }

    fun changeStatusBackToNew(id: Int,token: String) = flow<States<String>> {
        emit(States.loading())
        val service = apiInterface.changeStatusToBackNew(Constant.BAREARER + token,id)
        service.let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()?.message?: ""))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d("TAG", "changeStatusBackToNew: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }

    fun getParticipants(id: Int,token: String) = flow<States<List<LocalParticipants>>> {
        emit(States.loading())
        val getParticipantsService = apiInterface.getParticipants(Constant.BAREARER + token,id)
        getParticipantsService.let {
            if (it.isSuccessful && it.body() != null){
                emit(States.success(it.body()?.toGenerateParticipantsItem()?: listOf()))
            }else{
                emit(States.failed(it.message().toString()))
            }
        }
    }.catch {
        Log.d("TAG", "getParticipants: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }

    fun getImage(id: Int,token: String) = flow<States<List<com.candra.mydesiminationapp.data.source.local.LocalImage>>> {
        emit(States.loading())
        val getImageDissemination = apiInterface.getImage(Constant.BAREARER + token,id)
        getImageDissemination.let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()?.toGenerateImageItem()?: listOf()))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d("TAG", "getImage: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }

    fun getMemberStudent(id: Int,token: String) = flow<States<List<LocalMember>>> {
        emit(States.loading())
        val getMemberDissemination = apiInterface.showLecturer(Constant.BAREARER + token,id)
        getMemberDissemination.let {
            if (it.isSuccessful && it.body() != null){
                emit(States.success(it.body()?.members?.toGenerateMember()?: listOf()))
            }else{
                emit(States.failed(it.message().toString()))
            }
        }
    }.catch {
        Log.d("TAG", "getMemberStudent: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }

    fun getShowDisseminationStudent(token: String) = flow<States<IndexStudent>> {
        emit(States.loading())
        val getStudentMember = apiInterface.indexStudent(Constant.BAREARER + token)
        getStudentMember.let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        emit(States.failed(it.message.toString()))
        Log.d("TAG", "getShowDisseminationStudent: ${it.message.toString()}")
    }


    fun backToDraftRegisterStudent(token: String,id: Int) = flow<States<ChangeRegisterLecturer>> {
        val service = apiInterface.backToDraftRegisterStudent(Constant.BAREARER + token,id)
        service.let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        emit(States.failed(it.message.toString()))
        Log.d("TAG", "backToDraftRegisterStudent: ")
    }

    fun acceptMemberToDissemination(token: String,id: Int) = flow<States<String>> {
        val memberService = apiInterface.acceptMemberToDissemination(Constant.BAREARER + token,id)
        memberService.let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()?.message?:""))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        emit(States.failed(it.message.toString()))
        Log.d("TAG", "acceptMemberToDissemination: ${it.message.toString()}")
    }

    fun logStudent(token: String,id: Int) = flow<States<List<LocalLogDisseminationLecturerItem>>> {
        val logStudentService = apiInterface.logStudent(Constant.BAREARER + token,id)
        logStudentService.let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()?.toGenerateLocalStudent()?: listOf()))
            else emit(States.failed(it.message()))
        }
    }.catch {
        emit(States.failed(it.message.toString()))
        Log.d("TAG", "logStudent: ${it.message.toString()}")
    }

    fun correctionMember(token: String,positionId: Int,studentCode: String,statusId: Int,id: Int) = flow<States<String>> {
        val correctionMemberService = apiInterface.correctionMember(Constant.BAREARER + token,id,positionId,studentCode,statusId)
        correctionMemberService.let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()?.message?: ""))
            else emit(States.failed("${it.code()} => ${it.errorBody()}"))
        }
    }.catch {
        emit(States.failed(it.message.toString()))
        Log.d("TAG", "correctionMember: ${it.message.toString()}")
    }

    fun rejectMemberDissemination(token: String,id: Int) = flow<States<String>> {
        val rejectMemberService = apiInterface.rejectMemberDissemination(Constant.BAREARER + token,id)
        rejectMemberService.let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()?.message?:""))
            else emit(States.failed(it.errorBody().toString()))
        }
    }.catch {
        Log.d("TAG", "rejectMemberDissemination: ${it.message.toString()}")
        emit(States.failed(it.message.toString()))
    }

    fun addMemberDissemination(token:String, researchDisseminationId: Int, positionId: Int, studentCode: String) =
        flow<States<String>>
        {
            apiInterface.addMember(Constant.BAREARER + token,researchDisseminationId,positionId,studentCode)
            .let {
                if (it.isSuccessful && it.body() != null) emit(States.success(it.body()?.message?:""))
                else emit(States.failed("${it.code()} => ${it.errorBody()}"))
            }
        }.catch {
            emit(States.failed(it.message.toString()))
            Log.d("TAG", "addMemberDissemination: ${it.message.toString()}")
        }

    fun changeStatusStudent(token: String,researchDisseminationId: Int,statusId: Int,note: String) =
        flow<States<String>> {
            emit(States.loading())
            apiInterface.changeStatus(Constant.BAREARER + token,researchDisseminationId,statusId,note).let {
                if (it.isSuccessful && it.body() != null) emit(States.success(it.body()?.message?: ""))
                else emit(States.failed(it.message().toString()))
            }
        }.catch {
            emit(States.failed(it.message.toString()))
            Log.d(TAG, "changeStatusStudent: ${it.message.toString()}")
        }

    fun changeStatusLecturer(token: String,researchDisseminationId: Int,statusId: Int,note: String) =
        flow<States<String>> {
            emit(States.loading())
            apiInterface.changeStatus(Constant.BAREARER + token,researchDisseminationId,statusId,note).let {
                if (it.isSuccessful && it.body() != null) emit(States.success(it.body()?.message?:""))
                else emit(States.failed(it.message().toString()))
            }
        }.catch {
            emit(States.failed(it.message.toString()))
            Log.d(TAG, "changeStatusLecturer: ${it.message.toString()}")
        }

    fun updateDisseminationLecturer(token: String,
                                    id: Int,title: RequestBody,abstract: RequestBody,
    keywords: RequestBody, publisherId:RequestBody,publisherName: RequestBody,publisherUrl: RequestBody,
    publisherNumber: RequestBody,
    articleUrl: RequestBody,journalGradeId: RequestBody,journalYear: RequestBody,
    journalVolume: RequestBody,journalNumber: RequestBody,
    article: MultipartBody.Part,presentation: MultipartBody.Part,administration: MultipartBody.Part) =
        flow<States<String>>
        {
        emit(States.loading())
        val service = apiInterface.updateDisseminationStudent(Constant.BAREARER + token,
        id,title,abstract,keywords,publisherId,publisherName,publisherUrl,publisherNumber,articleUrl,journalGradeId,journalYear,journalVolume,
        journalNumber,article,presentation,administration)
        service.let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()?.message?: ""))
            else emit(States.failed(it.errorBody().toString()))
        }
    }.catch {
            emit(States.failed(it.message.toString()))
            Log.d("TAG", "updateDisseminationLecturer: ${it.message.toString()}")
        }

    fun loginStudent(code: String,password: String,tokenFcm: String) = flow<States<LoginStudentResponse>> {
        apiInterface.loginStudent(code,password,tokenFcm).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        emit(States.failed(it.message.toString()))
        Log.d("TAG", "loginStudent: ${it.message.toString()}")
    }

    fun loginAdministrator(username: String,password_one: String,password_two: String) = flow<States<ResponseLoginAdministrator>>
    {
        apiInterface.loginAdministrator(username,password_one,password_two).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        Log.d("TAG", "loginAdministrator: ${it.message.toString()}")
    }

    fun getPasswordTwoDigitAdministrator(email: String,password: String) = flow<States<String>> {
        apiInterface.getPasswordTwoAdmin(email,email,password).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!.digit?: ""))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        emit(States.failed(it.message.toString()))
        Log.d("TAG", "getPasswordTw oDigitAdministrator: ${it.message.toString()}")
    }

    fun showResearchDisseminationAdministrator(token: String,id: Int) = flow<States<ResponseShowDisseminationAdministrator>> {
        apiInterface.showResearchDisseminationAdmin(Constant.BAREARER + token,id).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()!!))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        emit(States.failed(it.message.toString()))
        Log.d("TAG", "showResearchDisseminationAdministrator: ${it.message.toString()}")
    }

    fun getIndexResearchDisseminationAdministrator(token: String) = flow<States<List<LocalIndexAdministratorItem>>>{
        apiInterface.getIndexResearchDisseminationAdmin(Constant.BAREARER + token).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()?.data?.toGenereteListIndexResearchDisseminationItem()?: listOf()))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        emit(States.failed(it.message.toString()))
        Log.d("TAG", "getIndexResearchDisseminationAdministrator: ${it.message.toString()}")
    }

    fun getLogResearchDisseminationAdministrator(token: String,id: Int) = flow<States<List<LocalLogDisseminationLecturerItem>>>{
        apiInterface.logResearchDisseminationAdmin(Constant.BAREARER + token,id).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()?.toGenerateLogResearchDisseminationAdmin()?: listOf()))
            else emit(States.failed(it.message().toString()))
        }
    }

    fun getParticipantsResearchDisseminationAdmin(token: String,id: Int) = flow<States<List<LocalParticipants>>> {
        apiInterface.getParticipantResearchDisseminationAdmin(Constant.BAREARER + token,id).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()?.toGenerateParticipantsAdminItem()?: listOf()))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        emit(States.failed(it.message.toString()))
        Log.d("TAG", "getParticipantsResearchDisseminationAdmin: ${it.message.toString()}")
    }

    fun getImageResearchDisseminationAdminItem(token:String,id: Int) = flow<States<List<LocalImage>>> {
        apiInterface.getImageResearchDisseminationAdmin(Constant.BAREARER + token,id).let {
            if (it.isSuccessful && it.body() != null) emit(States.success(it.body()?.toGenerateListImageItem()?: listOf()))
            else emit(States.failed(it.message().toString()))
        }
    }.catch {
        emit(States.failed(it.message.toString()))
        Log.d("TAG", "getImageResearchDisseminationAdminItem: ${it.message.toString()}")
    }

    fun postScheduleResearchDisseminationAdmin(token: String,id: Int,class_room_id: String,start_time: String,end_time: String,participantsSize: Int) =
        flow<States<String>> {
            apiInterface.postScheduleResearchDisseminationAdmin(Constant.BAREARER + token,id,class_room_id,start_time,end_time,participantsSize).let {
                if (it.isSuccessful && it.body() != null) emit(States.success(it.body()?.message?: ""))
                else emit(States.failed(it.message().toString()))
            }
        }.catch {
            Log.d("TAG", "postScheduleResearchDisseminationAdmin: ${it.message.toString()}")
        }

    fun getListClassResearchDisseminationAdmin(token: String,id: Int) = flow<States<List<LocalClassDisseminationItem>>>
    {
        apiInterface.getListClassResearchDissemination(Constant.BAREARER + token,id).let {
            if(it.isSuccessful && it.body() != null) emit(States.success(it.body()?.toGenerateClassListItem()?: listOf()))
            else emit(States.failed(it.message().toString()))
        }
    }

    fun changeStatusResearchDisseminationAdminItem(token: String,research_dissemination_id: Int,status_id: Int,note: String)
    = flow<States<String>> {
        apiInterface.changeStatusResearchDisseminationAdministrator(Constant.BAREARER + token,research_dissemination_id,status_id,note)
            .let {
                if (it.isSuccessful && it.body() != null) emit(States.success(it.body()?.message?: ""))
                else emit(States.failed(it.message().toString()))
            }
    }
}