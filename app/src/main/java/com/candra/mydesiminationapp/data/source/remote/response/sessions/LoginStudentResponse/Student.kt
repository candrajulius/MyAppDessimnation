package com.candra.mydesiminationapp.data.source.remote.response.sessions.LoginStudentResponse


import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("address")
    val address: String,
    @SerializedName("alumni_requirements_id")
    val alumniRequirementsId: Int,
    @SerializedName("approved_by")
    val approvedBy: String,
    @SerializedName("blood_id")
    val bloodId: Int,
    @SerializedName("classroom_id")
    val classroomId: Any,
    @SerializedName("code")
    val code: String,
    @SerializedName("completeness_id")
    val completenessId: Int,
    @SerializedName("country_id")
    val countryId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("current_class_name")
    val currentClassName: String,
    @SerializedName("current_semester_id")
    val currentSemesterId: Int,
    @SerializedName("current_stage_id")
    val currentStageId: Int,
    @SerializedName("current_year_id")
    val currentYearId: Int,
    @SerializedName("date_of_birth")
    val dateOfBirth: String,
    @SerializedName("district")
    val district: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("encrypted_password")
    val encryptedPassword: Any,
    @SerializedName("enter_id")
    val enterId: Int,
    @SerializedName("father_date_of_birth")
    val fatherDateOfBirth: String,
    @SerializedName("father_education_id")
    val fatherEducationId: Int,
    @SerializedName("father_income_id")
    val fatherIncomeId: Int,
    @SerializedName("father_name")
    val fatherName: String,
    @SerializedName("father_recent_work")
    val fatherRecentWork: String,
    @SerializedName("father_work_id")
    val fatherWorkId: Int,
    @SerializedName("feeder_id")
    val feederId: String,
    @SerializedName("feeder_institution_id")
    val feederInstitutionId: Any,
    @SerializedName("feeder_program_id")
    val feederProgramId: Any,
    @SerializedName("feeder_synchronize_id")
    val feederSynchronizeId: Int,
    @SerializedName("from_school")
    val fromSchool: String,
    @SerializedName("graduate_year")
    val graduateYear: Int,
    @SerializedName("guardian_date_of_birth")
    val guardianDateOfBirth: Any,
    @SerializedName("guardian_education_id")
    val guardianEducationId: Int,
    @SerializedName("guardian_income_id")
    val guardianIncomeId: Int,
    @SerializedName("guardian_name")
    val guardianName: String,
    @SerializedName("guardian_work_id")
    val guardianWorkId: Int,
    @SerializedName("handphone")
    val handphone: String,
    @SerializedName("healthcare_card_delivered_at")
    val healthcareCardDeliveredAt: Any,
    @SerializedName("healthcare_code")
    val healthcareCode: String,
    @SerializedName("healthcare_id")
    val healthcareId: Int,
    @SerializedName("healthcare_note")
    val healthcareNote: Any,
    @SerializedName("healthcare_room_id")
    val healthcareRoomId: Int,
    @SerializedName("healthcare_start_month")
    val healthcareStartMonth: Int,
    @SerializedName("healthcare_start_year")
    val healthcareStartYear: Int,
    @SerializedName("hobby")
    val hobby: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_content_type")
    val imageContentType: String,
    @SerializedName("image_file_name")
    val imageFileName: String,
    @SerializedName("image_file_size")
    val imageFileSize: Int,
    @SerializedName("image_updated_at")
    val imageUpdatedAt: String,
    @SerializedName("institution_id")
    val institutionId: Int,
    @SerializedName("last_education_document_content_type")
    val lastEducationDocumentContentType: Any,
    @SerializedName("last_education_document_file_name")
    val lastEducationDocumentFileName: Any,
    @SerializedName("last_education_document_file_size")
    val lastEducationDocumentFileSize: Any,
    @SerializedName("last_education_document_updated_at")
    val lastEducationDocumentUpdatedAt: Any,
    @SerializedName("last_education_id")
    val lastEducationId: Int,
    @SerializedName("lecturer_id")
    val lecturerId: Any,
    @SerializedName("line_messenger_id")
    val lineMessengerId: Any,
    @SerializedName("mother_date_of_birth")
    val motherDateOfBirth: String,
    @SerializedName("mother_education_id")
    val motherEducationId: Int,
    @SerializedName("mother_income_id")
    val motherIncomeId: Int,
    @SerializedName("mother_name")
    val motherName: String,
    @SerializedName("mother_recent_work")
    val motherRecentWork: String,
    @SerializedName("mother_work_id")
    val motherWorkId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("nasionality_id")
    val nasionalityId: Int,
    @SerializedName("national_student_identification_number")
    val nationalStudentIdentificationNumber: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("other_source_of_information")
    val otherSourceOfInformation: String,
    @SerializedName("parent_address")
    val parentAddress: String,
    @SerializedName("parent_handphone")
    val parentHandphone: String,
    @SerializedName("parent_phone")
    val parentPhone: Any,
    @SerializedName("password_salt")
    val passwordSalt: Any,
    @SerializedName("perishable_token")
    val perishableToken: Any,
    @SerializedName("person_feeder_id")
    val personFeederId: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("place_of_birth")
    val placeOfBirth: String,
    @SerializedName("postal_code")
    val postalCode: String,
    @SerializedName("profile_status_id")
    val profileStatusId: Int,
    @SerializedName("program_id")
    val programId: Int,
    @SerializedName("province")
    val province: String,
    @SerializedName("ravine")
    val ravine: String,
    @SerializedName("recent_achievements")
    val recentAchievements: String,
    @SerializedName("recent_university_id")
    val recentUniversityId: Int,
    @SerializedName("recent_university_name")
    val recentUniversityName: String,
    @SerializedName("recent_work")
    val recentWork: String,
    @SerializedName("recent_work_month")
    val recentWorkMonth: Int,
    @SerializedName("recent_work_position")
    val recentWorkPosition: String,
    @SerializedName("recent_work_year")
    val recentWorkYear: Int,
    @SerializedName("recognized_sks")
    val recognizedSks: Any,
    @SerializedName("recommended_by")
    val recommendedBy: String,
    @SerializedName("religion_id")
    val religionId: Int,
    @SerializedName("required_information_description")
    val requiredInformationDescription: Any,
    @SerializedName("required_information_status_id")
    val requiredInformationStatusId: Int,
    @SerializedName("resident_card_id")
    val residentCardId: String,
    @SerializedName("resident_card_image_content_type")
    val residentCardImageContentType: String,
    @SerializedName("resident_card_image_file_name")
    val residentCardImageFileName: String,
    @SerializedName("resident_card_image_file_size")
    val residentCardImageFileSize: Int,
    @SerializedName("resident_card_image_updated_at")
    val residentCardImageUpdatedAt: String,
    @SerializedName("resident_card_type_id")
    val residentCardTypeId: Int,
    @SerializedName("rt_id")
    val rtId: String,
    @SerializedName("rw_id")
    val rwId: String,
    @SerializedName("scholarship_id")
    val scholarshipId: Int,
    @SerializedName("scholarship_notes")
    val scholarshipNotes: Any,
    @SerializedName("scholarship_percentage")
    val scholarshipPercentage: Int,
    @SerializedName("scholarship_recommendation_id")
    val scholarshipRecommendationId: Int,
    @SerializedName("school_majors")
    val schoolMajors: String,
    @SerializedName("selected_class_id")
    val selectedClassId: Int,
    @SerializedName("sex_id")
    val sexId: Int,
    @SerializedName("sibling_who_went_to_same_university")
    val siblingWhoWentToSameUniversity: String,
    @SerializedName("signup_date")
    val signupDate: String,
    @SerializedName("source_of_information_id")
    val sourceOfInformationId: Int,
    @SerializedName("starting_stage_id")
    val startingStageId: Int,
    @SerializedName("starting_year_id")
    val startingYearId: Int,
    @SerializedName("status_id")
    val statusId: Int,
    @SerializedName("stay_id")
    val stayId: Int,
    @SerializedName("synchronize_id")
    val synchronizeId: Int,
    @SerializedName("temp_password")
    val tempPassword: String,
    @SerializedName("town")
    val town: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("village")
    val village: String,
    @SerializedName("visible_id")
    val visibleId: Int
)