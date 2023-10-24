package com.candra.mydesiminationapp.data.source.remote.response.sessions.loginresponse


import com.google.gson.annotations.SerializedName

data class Lecturer(
    @SerializedName("active_status_id")
    val activeStatusId: Int,
    @SerializedName("address")
    val address: String,
    @SerializedName("bond_id")
    val bondId: Int,
    @SerializedName("certification_id")
    val certificationId: Int,
    @SerializedName("certification_institution_number")
    val certificationInstitutionNumber: Any,
    @SerializedName("certification_number")
    val certificationNumber: Any,
    @SerializedName("certification_year_id")
    val certificationYearId: Int,
    @SerializedName("civil_servant_identification_number")
    val civilServantIdentificationNumber: Any,
    @SerializedName("code")
    val code: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("credit_score")
    val creditScore: String,
    @SerializedName("current_program_id")
    val currentProgramId: Int,
    @SerializedName("current_stage_id")
    val currentStageId: Int,
    @SerializedName("current_year_id")
    val currentYearId: Int,
    @SerializedName("date_of_birth")
    val dateOfBirth: String,
    @SerializedName("date_of_first_degree")
    val dateOfFirstDegree: String,
    @SerializedName("date_of_second_degree")
    val dateOfSecondDegree: String,
    @SerializedName("date_of_third_degree")
    val dateOfThirdDegree: String,
    @SerializedName("designation_decree_date_at")
    val designationDecreeDateAt: Any,
    @SerializedName("designation_decree_number")
    val designationDecreeNumber: Any,
    @SerializedName("email")
    val email: String,
    @SerializedName("encrypted_password")
    val encryptedPassword: String,
    @SerializedName("feeder_id")
    val feederId: String,
    @SerializedName("first_degree_major")
    val firstDegreeMajor: String,
    @SerializedName("first_degree_title")
    val firstDegreeTitle: String,
    @SerializedName("first_degree_university")
    val firstDegreeUniversity: String,
    @SerializedName("front_title")
    val frontTitle: String,
    @SerializedName("garuda_id")
    val garudaId: Any,
    @SerializedName("google_scholar_id")
    val googleScholarId: Any,
    @SerializedName("grade_id")
    val gradeId: Int,
    @SerializedName("handphone")
    val handphone: String,
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
    @SerializedName("name")
    val name: String,
    @SerializedName("orcid_id")
    val orcidId: Any,
    @SerializedName("password_reset_sent_at")
    val passwordResetSentAt: String,
    @SerializedName("password_reset_token")
    val passwordResetToken: String,
    @SerializedName("password_salt")
    val passwordSalt: String,
    @SerializedName("person_feeder_id")
    val personFeederId: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("place_of_birth")
    val placeOfBirth: String,
    @SerializedName("program_feeder_id")
    val programFeederId: String,
    @SerializedName("publication_id")
    val publicationId: Int,
    @SerializedName("rank_id")
    val rankId: Int,
    @SerializedName("rear_title")
    val rearTitle: String,
    @SerializedName("religion_id")
    val religionId: Int,
    @SerializedName("resident_card_id")
    val residentCardId: Any,
    @SerializedName("salary")
    val salary: String,
    @SerializedName("scopus_id")
    val scopusId: Any,
    @SerializedName("second_degree_major")
    val secondDegreeMajor: String,
    @SerializedName("second_degree_title")
    val secondDegreeTitle: String,
    @SerializedName("second_degree_university")
    val secondDegreeUniversity: String,
    @SerializedName("sex_id")
    val sexId: Int,
    @SerializedName("short_name")
    val shortName: String,
    @SerializedName("sinta_id")
    val sintaId: Int,
    @SerializedName("starting_date_at")
    val startingDateAt: Any,
    @SerializedName("status_id")
    val statusId: Int,
    @SerializedName("structural_id")
    val structuralId: Int,
    @SerializedName("tax_code")
    val taxCode: String,
    @SerializedName("third_degree_major")
    val thirdDegreeMajor: String,
    @SerializedName("third_degree_title")
    val thirdDegreeTitle: String,
    @SerializedName("third_degree_university")
    val thirdDegreeUniversity: String,
    @SerializedName("type_id")
    val typeId: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("visible_id")
    val visibleId: Int,
    @SerializedName("wos_researcher_id")
    val wosResearcherId: Any
)