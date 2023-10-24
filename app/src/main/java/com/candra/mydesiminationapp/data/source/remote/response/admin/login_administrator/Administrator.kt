package com.candra.mydesiminationapp.data.source.remote.response.admin.login_administrator


import com.google.gson.annotations.SerializedName

data class Administrator(
    @SerializedName("avatar_content_type")
    val avatarContentType: String,
    @SerializedName("avatar_file_name")
    val avatarFileName: String,
    @SerializedName("avatar_file_size")
    val avatarFileSize: Int,
    @SerializedName("avatar_updated_at")
    val avatarUpdatedAt: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("current_alumni_stage_id")
    val currentAlumniStageId: Int,
    @SerializedName("current_attendance_date")
    val currentAttendanceDate: String,
    @SerializedName("current_attendance_location_id")
    val currentAttendanceLocationId: Int,
    @SerializedName("current_faculty_abbreviation")
    val currentFacultyAbbreviation: String,
    @SerializedName("current_faculty_id")
    val currentFacultyId: Int,
    @SerializedName("current_faculty_title")
    val currentFacultyTitle: String,
    @SerializedName("current_program_abbreviation")
    val currentProgramAbbreviation: String,
    @SerializedName("current_program_id")
    val currentProgramId: Int,
    @SerializedName("current_program_title")
    val currentProgramTitle: String,
    @SerializedName("current_stage_id")
    val currentStageId: Int,
    @SerializedName("current_year_id")
    val currentYearId: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("employees_page")
    val employeesPage: Int,
    @SerializedName("employees_search")
    val employeesSearch: String,
    @SerializedName("encrypted_password")
    val encryptedPassword: String,
    @SerializedName("final_terms_page")
    val finalTermsPage: Int,
    @SerializedName("final_terms_search")
    val finalTermsSearch: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lecturers_page")
    val lecturersPage: Int,
    @SerializedName("lecturers_search")
    val lecturersSearch: String,
    @SerializedName("login")
    val login: String,
    @SerializedName("middle_terms_page")
    val middleTermsPage: Int,
    @SerializedName("middle_terms_search")
    val middleTermsSearch: Any,
    @SerializedName("name")
    val name: String,
    @SerializedName("password2")
    val password2: String,
    @SerializedName("password2_sample")
    val password2Sample: Any,
    @SerializedName("password_salt")
    val passwordSalt: String,
    @SerializedName("problem_box_limit")
    val problemBoxLimit: String,
    @SerializedName("program_limit")
    val programLimit: String,
    @SerializedName("roles_mask")
    val rolesMask: Int,
    @SerializedName("status_id")
    val statusId: Int,
    @SerializedName("students_page")
    val studentsPage: Int,
    @SerializedName("students_program_id")
    val studentsProgramId: Int,
    @SerializedName("students_search")
    val studentsSearch: String,
    @SerializedName("students_stage_id")
    val studentsStageId: Int,
    @SerializedName("students_status_id")
    val studentsStatusId: Int,
    @SerializedName("students_year_id")
    val studentsYearId: Int,
    @SerializedName("study_card_code1")
    val studyCardCode1: String,
    @SerializedName("study_card_code2")
    val studyCardCode2: String,
    @SerializedName("study_cards_page")
    val studyCardsPage: Int,
    @SerializedName("study_cards_search")
    val studyCardsSearch: Any,
    @SerializedName("updated_at")
    val updatedAt: String
)