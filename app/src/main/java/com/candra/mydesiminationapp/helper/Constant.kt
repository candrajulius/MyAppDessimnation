package com.candra.mydesiminationapp.helper

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constant{
    const val BAREARER = "Bearer "
    const val TERM = "term"
    const val BASE_URL = "http://172.104.185.49:3000/"
    const val QUERY_STUDENT = "reference_lists/students.json"
    const val LOGIN_LECTURER = "api/sessions/lecturer"
    const val CREATE_DESIMINATION = "lecturer/research_disseminations.json"
    const val LOG_LECTURER = "lecturer/research_disseminations/{id}/logs.json"
    const val INDEX_LECTURER = "lecturer/research_disseminations.json"
    const val CHANGE_TO_DRAFT_REGISTER = "lecturer/research_disseminations/{id}/draft_registration.json"
    const val CHANGE_STATUS = "lecturer/research_dissemination_logs.json"
    const val UPDATE_LECTURER = "lecturer/research_disseminations/{id}.json"
    const val CHANGE_STATUST_TO_BACK_NEW = "lecturer/research_disseminations/{id}/back_to_new.json"
    const val SHOW_DESIMINATION_LECTURER = "lecturer/research_disseminations/{id}.json"
    const val IMAGE_LECTURER = "lecturer/research_disseminations/{id}/images.json"
    const val PARTICIPANTS_LECTURER = "lecturer/research_disseminations/{id}/participants.json"
    const val AUTHORIZATION = "Authorization"
    const val PATH_ID = "id"
    const val RESEARCH_DESIMINATION_ID = "research_dissemination_id"
    const val STATUS_ID = "status_id"
    const val LEADER_DISSEMINATAION = "Kepala Peneliti"
    const val MEMBER_DISSEMINATION = "anggota peneliti"
    const val NOTE = "note"
    const val POSITON_ID = "position_id"
    const val STUDENT_CODE = "student_code"
    const val TIME_DELAYED = 5000L
    const val URL_IMAGE_SISKA_DISSEMINATION = "https://raw.githubusercontent.com/candrajulius/My_App_Desimination/main/siska_logo.png"
    const val STUDENT = "student"
    const val LECTURER = "lecturer"
    const val ADMINISTRATOR = "admin"
    const val LEADER_STUDENT = "Kepala Peneliti"
    const val EXTRA_STUDENT_MEMBER_ID = "extra_student_member_id"
    const val EXTRA_TOKEN = "extra_token"
    const val EXTRA_TOKEN_ADMIN = "extra_token_admin"
    const val EXTRA_CHANGE_STATUS_MEMBER = "extra_change_status_member"

    // API for Admin
    // SESSION LOGIN ADMIN
    const val API_LOGIN_ADMIN = "api/sessions/administrator"
    const val API_GET_PASSWORD_TWO_ADMIN = "api/sessions/administrator_second_password_digit"
    // SESSION LOGIN ADMIN
    // API RESEARCH DISSEMINATION ADMIN
    const val API_INDEX_RESEARCH_DISSEMINATION_ADMIN = "administrator/research_disseminations.json"
    const val API_SHOW_RESEARCH_DISSEMINATION_ADMIN = "administrator/research_disseminations/{id}.json"
    const val API_LOG_RESEARCH_DISSEMINATION_ADMIN = "administrator/research_disseminations/{id}.json"
    const val API_PARTICIPANTS_RESEARCH_DISSEMINATION_ADMIN = "administrator/research_disseminations/{id}/participants.json"
    const val API_IMAGE_RESEARCH_DISSEMINATION_ADMIN = "administrator/research_disseminations/{id}/images.json"
    const val API_SCHEDULE_RESEARCH_DISSEMINATION_ADMIN = "administrator/research_disseminations/{id}.json"
    const val API_CLASS_LIST_RESEARCH_DISSEMINATION_ADMIN = "administrator/research_disseminations/{id}/edit.json"
    const val API_CHANGE_PRODI_RESEARCH_DISSEMINATION_ADMIN = "administrator/research_disseminations/change_program.json"
    const val API_CHANGE_STATUS_RESEARCH_DISSEMINATION_ADMIN = "administrator/research_dissemination_logs.json"
    // API RESEARCH DISSEMINATION ADMIN
    // API FOR ADMIN

    // ATTRIBUTE FOR ADMIN
    // ATTRIBUTE API LOGIN
    const val USERNAME = "username"
    const val PASSWORD1 = "password1"
    const val PASSWORD2 = "password2"


    // ATTRIBUTE API UPDATE SCHEDULE DISSEMINATION ADMIN
    const val CLASS_ROOM_ID = "class_room_id"
    const val START_TIME = "start_time"
    const val END_TIME = "end_time"
    const val PARTICIPANT_SIZE = "participant_size"

    // ATTRIBUTE API CHANGE PRODI RESEARCH DISSEMINATION
    const val PROGRAM_ID = "program_id"

    // ATTRIBUTE API CHANGE STATUS RSEARCH DISSEMINATION
    // ATTRIBUTE FOR ADMIN

    const val INDEX_STUDENT = "student/research_disseminations.json"
    const val LOG_STUDENT = "student/research_disseminations/{id}/logs.json"
    const val GET_IMAGE_STUDENT = "student/research_disseminations/{id}/images.json"
    const val GET_PARTICIPANTS_STUDENT = "student/research_disseminations/{id}/participants.json"
    const val COMPILE_AGAIN = "student/research_disseminations/{id}/recompile.json"
    const val LOGIN_STUDENT = "api/sessions/student"
    const val CHANGE_STATUS_STUDENT = "student/research_dissemination_logs.json"
    const val CORRECTION_MEMBER = "student/research_dissemination_members/{id}.json"
    const val ADD_MEMBER = "student/research_dissemination_members.json"
    const val BACK_TO_DRAFT_REPORT = "student/research_disseminations/{id}/draft_report.json"
    const val DRAFT_REGISTER = "student/research_disseminations/{id}/draft_registration.json"
    const val GET_EDIT = "student/research_disseminations/{id}/edit.json"
    const val UPDATE_DISSEMINATION_STUDENT = "student/research_disseminations/{id}.json"
    const val RECEIPT_MEMBER_TO_DISSEMINAITON = "student/research_dissemination_members/{id}/agree.json"
    const val REJECT_MEMBER_DISSEMINATION = "student/research_dissemination_members/{id}/reject.json"
    const val EXTRA_STUDENT_MEMBER = "extra_student_member"


    // Form for login lecturer
    const val EMAIL = "email"
    const val PASSWORD = "password"

    // From for login student
    const val CODE = "code"
    const val FCM_TOKEN = "fcm_token"

    // form for create desimination
    const val STUDENT_ID = "student_id"


    const val EXTRA_NEW_MEMBER = "extra_new_member"

    const val EMPTY_TEXT = "Tidak ada catatan desiminasi penelitian disini"

    const val TITTLE_LOG = "Catatan Desiminasi Penelitian"
    const val TITTLE_DISSEMINATION = "Desiminasi Penelitian"
    const val TITLE = "[research_dissemination]title"
    const val ABSTRACT = "[research_dissemination]abstract"
    const val KEYWORDS = "[research_dissemination]keywords"
    const val PUBLISHER_ID = "[research_dissemination]publisher_id"
    const val PUBLISHER_NAME = "research_dissemination]publisher_name"
    const val PUBLISHER_NUMBER = "[research_dissemination]publisher_number"
    const val PUBLISHER_URL = "[research_dissemination]publisher_url"
    const val ARTICLE_URL = "[research_dissemination]article_url"
    const val JOURNAL_GRADE_ID ="[research_dissemination]journal_grade_id"
    const val JOURNAL_YEAR = "[research_dissemination]journal_year"
    const val JOURNAL_VOLUME = "[research_dissemination]journal_volume"
    const val JOURNAL_NUMBER = "[research_dissemination]journal_number"
    const val CONFERENCE_GRADE_ID = "[research_dissemination]conference_grade_id"
    const val CONFERENCE_LOCATION = "[research_dissemination]conference_location"
    const val CONFERENCE_START_DATE = "[research_dissemination]conference_start_date"
    const val CONFERENCE_END_DATE = "[research_dissemination]conference_end_date"
    const val ARTICLE = "[research_dissemination]article"
    const val PRESENTATION = "[research_dissemination]presentation"
    const val ADMINISTRATION = "[research_dissemination]administration"
    const val EXTRA_JOURNAL = "extra_journal"
    const val EXTRA_GRADE_JOURNAL = "grade_journal"

    // Sebagai anggota peneliti keberapa
    const val MEMBER_ONE = "Anggota Peneliti 1"
    const val MEMBER_TWO = "Anggota Peneliti 2"
    const val MEMBER_THREE = "Anggota Peneliti 3"
    const val MEMBER_FOUR = "Anggota Peneliti 4"
    const val MEMBER_ZERO = "Anggota Peneliti 0"

    const val JOURNAL_INTERNATIONAL_REPUTATION = "Jurnal Internasional Bereputasi"
    const val JOURNAL_INTERNATIONAL = "Jurnal Internasional"
    const val JOURNAL_NATIONAL_REPUTATION = "Jurnal Nasional Terakreditasi"
    const val JOURNAL_NATIONAL = "Jurnal Nasional"


    const val EXTRA_STUDENT_ID = "student_id"
    const val STATUS_NEW = "Baru"
    const val STATUS_DRAFT_REGISTER = "Draft Pendaftaran"
    const val STATUS_DRAFT_REPORT = "Draft Laporan"
    const val STATUS_REGISTER_IS_SUBMITTED_SUPERVISING_LECTURER = "Pendaftaran Diajukan ke Dosen Pembimbing"
    const val STATUS_REGISTER_REJECTED_LECTURER = "Pendaftaran Ditolak Dosen Pembimbing"
    const val STATUS_REGISTER_IS_SUBMITTED_PROGRAM_STUDY = "Pendaftaran Diajukan ke Program Studi"
    const val STATUS_REGISTER_APPROVE_PROGRAM_STUDY = "Pendaftaran Disetujui Program Studi"
    const val STATUS_REGISTER_REJECTED_PROGRAM_STUDY = "Pendaftaran Ditolak Program Studi"
    const val STATUS_REPORT_APPROVE_PROGRAM_STUDY = "Laporan Disetujui Program Studi"
    const val STATUS_REPORT_SUBMIT_PROGRAM_STUDY = "Laporan Diajukan ke Program Studi"
    const val STATUS_REPORT_REJECTED_PROGRAM_STUDY = "Laporan Ditolak Program Studi"
    const val STATUS_REPORT_REJECTED_LECTURER = "Laporan Ditolak Dosen Pembimbing"
    const val ARTICLE_SUBMITTED_TO_PROGRAM_STUDY = "Artikel Diajukan ke Program Studi"
    const val STATUS_REPORT_APPROVED_BY_THE_DEAN = "Laporan Disahkan Dekanat"


}