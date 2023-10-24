package com.candra.mydesiminationapp.data.source.remote.network

import com.candra.mydesiminationapp.data.source.remote.response.RequestLeaderDissemination
import com.candra.mydesiminationapp.data.source.remote.response.admin.change_status_dissemination_administrator.ResponseChangeStatusResearchDisseminationAdministrator
import com.candra.mydesiminationapp.data.source.remote.response.admin.images_dissemination_administrator.ResponseImagesResearchDisseminationAdministrator
import com.candra.mydesiminationapp.data.source.remote.response.admin.index_dissemination_administrator.DeanReportResearchDisseminations
import com.candra.mydesiminationapp.data.source.remote.response.admin.list_class_dissemination_administrator.ResponseListResearchDisseminationAdministrator
import com.candra.mydesiminationapp.data.source.remote.response.admin.log_dissemination_administrator.ResponseLogResearchDissemination
import com.candra.mydesiminationapp.data.source.remote.response.admin.login_administrator.ResponseGetPasswordTwo
import com.candra.mydesiminationapp.data.source.remote.response.admin.login_administrator.ResponseLoginAdministrator
import com.candra.mydesiminationapp.data.source.remote.response.admin.participants_dissemination_administrator.ResponseParticipantsAdministrator
import com.candra.mydesiminationapp.data.source.remote.response.admin.show_administrator_dissemination.ResponseShowDisseminationAdministrator
import com.candra.mydesiminationapp.data.source.remote.response.admin.update_schedule_dissemination_administrator.ResponseUpdateScheduleDisseminationAdministrator
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.BackToNew
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.change_register.ChangeRegisterLecturer
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.change_status_lecturer.ChangeStatusLecturer
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.create.CreateDesiminationLecturer
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.image.ImageDesiminationLecturer
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.index.IndexDisseminationResponse
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.log.LogDisseminationResponse
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.participants.ParticipantsDesiminationLecturer
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.show.ShowLecturerDesimination
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.update.UpdateDesiminationLecturer
import com.candra.mydesiminationapp.data.source.remote.response.sessions.LoginStudentResponse.LoginStudentResponse
import com.candra.mydesiminationapp.data.source.remote.response.sessions.loginresponse.LoginLecturer
import com.candra.mydesiminationapp.data.source.remote.response.student.CorrectionMemberResponse
import com.candra.mydesiminationapp.data.source.remote.response.student.RejectMemberResponse
import com.candra.mydesiminationapp.data.source.remote.response.student.UpdateDisseminationStudent
import com.candra.mydesiminationapp.data.source.remote.response.student.add_member.AddMemberResponse
import com.candra.mydesiminationapp.data.source.remote.response.student.index.IndexStudent
import com.candra.mydesiminationapp.data.source.remote.response.student.log_student.LogStudent
import com.candra.mydesiminationapp.helper.Constant
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.FileNotFoundException
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @FormUrlEncoded
    @POST(Constant.LOGIN_LECTURER)
    suspend fun loginLecturer(
        @Field(Constant.EMAIL) email: String,
        @Field(Constant.PASSWORD) password: String
    ): Response<LoginLecturer>

    @GET(Constant.LOG_LECTURER)
    suspend fun getLogsLecturer(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path(Constant.PATH_ID) id: Int
    ): Response<LogDisseminationResponse>

    @GET(Constant.INDEX_LECTURER)
    suspend fun getIndexLecturer(
        @Header(Constant.AUTHORIZATION) token: String
    ): Response<IndexDisseminationResponse>

    @GET(Constant.SHOW_DESIMINATION_LECTURER)
    suspend fun showLecturer(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path(Constant.PATH_ID) id: Int
    ): Response<ShowLecturerDesimination>

    @FormUrlEncoded
    @POST(Constant.CREATE_DESIMINATION)
    suspend fun createDesimination(
        @Header(Constant.AUTHORIZATION) token: String,
        @Field(Constant.STUDENT_ID) student_id: Int
    ): Response<CreateDesiminationLecturer>

    @FormUrlEncoded
    @PUT(Constant.UPDATE_LECTURER)
    suspend fun updateDesiminationLecturer(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path(Constant.PATH_ID) id: Int,
        @Field(Constant.STUDENT_ID) student_id: Int
    ): Response<UpdateDesiminationLecturer>

    @FormUrlEncoded
    @POST(Constant.CHANGE_STATUS)
    suspend fun changeStatus(
        @Header(Constant.AUTHORIZATION) token: String,
        @Field(Constant.RESEARCH_DESIMINATION_ID) research_desimination_id: Int,
        @Field(Constant.STATUS_ID) status_id: Int,
        @Field(Constant.NOTE) note: String
    ): Response<ChangeStatusLecturer>

    @FormUrlEncoded
    @POST(Constant.CHANGE_STATUS_STUDENT)
    suspend fun changeStatusStudent(
        @Header(Constant.AUTHORIZATION) token: String,
        @Field(Constant.RESEARCH_DESIMINATION_ID) research_desimination_id: Int,
        @Field(Constant.STATUS_ID) statusId: Int,
        @Field(Constant.NOTE) note: String
    ): Response<ChangeStatusLecturer>

    @POST(Constant.CHANGE_TO_DRAFT_REGISTER)
    suspend fun changeDraftRegister(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path(Constant.PATH_ID) id: Int
    ): Response<ChangeRegisterLecturer>

    @GET(Constant.IMAGE_LECTURER)
    suspend fun getImage(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path(Constant.PATH_ID) id: Int
    ): Response<ImageDesiminationLecturer>

    @GET(Constant.PARTICIPANTS_LECTURER)
    suspend fun getParticipants(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path(Constant.PATH_ID) id: Int
    ): Response<ParticipantsDesiminationLecturer>

    @POST(Constant.CHANGE_STATUST_TO_BACK_NEW)
    suspend fun changeStatusToBackNew(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path(Constant.PATH_ID) id: Int
    ): Response<BackToNew>

    @GET(Constant.QUERY_STUDENT)
    suspend fun getQueryStudent(
        @Header(Constant.AUTHORIZATION) token: String,
        @Query(Constant.TERM) term: String
    ): RequestLeaderDissemination

    @FormUrlEncoded
    @POST(Constant.LOGIN_STUDENT)
    suspend fun loginStudent(
        @Field(Constant.CODE) code: String,
        @Field(Constant.PASSWORD) password: String,
        @Field(Constant.FCM_TOKEN) token_fcm: String
    ): Response<LoginStudentResponse>

    @GET(Constant.INDEX_STUDENT)
    suspend fun indexStudent(
        @Header(Constant.AUTHORIZATION) token: String
    ): Response<IndexStudent>

    @GET(Constant.LOG_STUDENT)
    suspend fun logStudent(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path(Constant.PATH_ID) id: Int
    ): Response<LogStudent>

    @FormUrlEncoded
    @POST(Constant.ADD_MEMBER)
    suspend fun addMember(
        @Header(Constant.AUTHORIZATION) token: String,
        @Field(Constant.RESEARCH_DESIMINATION_ID) research_desimination_id: Int,
        @Field(Constant.POSITON_ID) position_id: Int,
        @Field(Constant.STUDENT_CODE) student_code: String
    ): Response<AddMemberResponse>

    @POST(Constant.DRAFT_REGISTER)
    suspend fun backToDraftRegisterStudent(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path(Constant.PATH_ID) id: Int
    ): Response<ChangeRegisterLecturer>

    @POST(Constant.RECEIPT_MEMBER_TO_DISSEMINAITON)
    suspend fun acceptMemberToDissemination(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path(Constant.PATH_ID) id: Int
    ): Response<UpdateDisseminationStudent>

    @POST(Constant.REJECT_MEMBER_DISSEMINATION)
    suspend fun rejectMemberDissemination(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path(Constant.PATH_ID) id: Int
    ): Response<RejectMemberResponse>

    @FormUrlEncoded
    @PUT(Constant.CORRECTION_MEMBER)
    suspend fun correctionMember(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path(Constant.PATH_ID) id: Int,
        @Field(Constant.POSITON_ID) position_id: Int,
        @Field(Constant.STUDENT_CODE) student_code: String,
        @Field(Constant.STATUS_ID) status_id: Int
    ): Response<CorrectionMemberResponse>

    @Multipart
    @PUT(Constant.UPDATE_DISSEMINATION_STUDENT)
    suspend fun updateDisseminationStudent(
        @Header(Constant.AUTHORIZATION) token:String,
        @Path(Constant.PATH_ID) id: Int,
        @Part (Constant.TITLE) title: RequestBody,
        @Part(Constant.ABSTRACT) abstract: RequestBody,
        @Part(Constant.KEYWORDS) keywords: RequestBody,
        @Part(Constant.PUBLISHER_ID) publisher_id: RequestBody,
        @Part(Constant.PUBLISHER_NAME) publisher_name: RequestBody,
        @Part(Constant.PUBLISHER_NUMBER) publisher_number: RequestBody,
        @Part(Constant.PUBLISHER_URL) publisherUrl: RequestBody,
        @Part(Constant.ARTICLE_URL) article_url: RequestBody,
        @Part(Constant.JOURNAL_GRADE_ID) journal_grade_id: RequestBody,
        @Part(Constant.JOURNAL_YEAR) journal_year: RequestBody,
        @Part(Constant.JOURNAL_VOLUME) journal_volume: RequestBody,
        @Part(Constant.JOURNAL_NUMBER) journal_number: RequestBody,
        @Part article: MultipartBody.Part,
        @Part presentation: MultipartBody.Part,
        @Part administration: MultipartBody.Part,
    ): Response<UpdateDisseminationStudent>

    // ADMIN
    @FormUrlEncoded
    @POST(Constant.API_LOGIN_ADMIN)
    suspend fun loginAdministrator(
        @Field(Constant.USERNAME) username: String,
        @Field(Constant.PASSWORD1) password: String,
        @Field(Constant.PASSWORD2) password_2: String
    ): Response<ResponseLoginAdministrator>

    @FormUrlEncoded
    @POST(Constant.API_GET_PASSWORD_TWO_ADMIN)
    suspend fun getPasswordTwoAdmin(
        @Query(Constant.USERNAME) username: String,
        @Field(Constant.EMAIL) email: String,
        @Field(Constant.PASSWORD) password: String
    ):Response<ResponseGetPasswordTwo>

    @GET(Constant.API_INDEX_RESEARCH_DISSEMINATION_ADMIN)
    suspend fun getIndexResearchDisseminationAdmin(
        @Header(Constant.AUTHORIZATION) token: String
    ): Response<DeanReportResearchDisseminations>

    @GET(Constant.API_SHOW_RESEARCH_DISSEMINATION_ADMIN)
    suspend fun showResearchDisseminationAdmin(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path(Constant.PATH_ID) id: Int
    ): Response<ResponseShowDisseminationAdministrator>

    @GET(Constant.API_LOG_RESEARCH_DISSEMINATION_ADMIN)
    suspend fun logResearchDisseminationAdmin(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path(Constant.PATH_ID) id: Int
    ): Response<ResponseLogResearchDissemination>

    @GET(Constant.API_PARTICIPANTS_RESEARCH_DISSEMINATION_ADMIN)
    suspend fun getParticipantResearchDisseminationAdmin(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path(Constant.PATH_ID) id: Int
    ): Response<ResponseParticipantsAdministrator>

    @GET(Constant.API_IMAGE_RESEARCH_DISSEMINATION_ADMIN)
    suspend fun getImageResearchDisseminationAdmin(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path(Constant.PATH_ID) id: Int
    ):Response<ResponseImagesResearchDisseminationAdministrator>

    @FormUrlEncoded
    @POST(Constant.API_SCHEDULE_RESEARCH_DISSEMINATION_ADMIN)
    suspend fun postScheduleResearchDisseminationAdmin(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path(Constant.PATH_ID) id: Int,
        @Field(Constant.CLASS_ROOM_ID) class_room_id: String,
        @Field(Constant.START_TIME) start_time: String,
        @Field(Constant.END_TIME) end_time: String,
        @Field(Constant.PARTICIPANT_SIZE) participant_size: Int
    ): Response<ResponseUpdateScheduleDisseminationAdministrator>

    @GET(Constant.API_CLASS_LIST_RESEARCH_DISSEMINATION_ADMIN)
    suspend fun getListClassResearchDissemination(
        @Header(Constant.AUTHORIZATION) token: String,
        @Path(Constant.PATH_ID) path_id: Int,
    ): Response<ResponseListResearchDisseminationAdministrator>

    @FormUrlEncoded
    @POST(Constant.API_CHANGE_STATUS_RESEARCH_DISSEMINATION_ADMIN)
    suspend fun changeStatusResearchDisseminationAdministrator(
        @Header(Constant.AUTHORIZATION) token: String,
        @Field(Constant.RESEARCH_DESIMINATION_ID) research_dissemination_id: Int,
        @Field(Constant.STATUS_ID) status_id:Int,
        @Field(Constant.NOTE) note: String
    ): Response<ResponseChangeStatusResearchDisseminationAdministrator>

}
