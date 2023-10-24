package com.candra.mydesiminationapp.data.source.remote.network

data class TemptLoginLecturer(
    val id: Int? = null,
    val name: String? = null,
    val short_name: String? = null,
    val rear_title: String? = null,
    val sex_id: Int? = null,
    val address: String? = null,
)

data class GetTokenLecturer(
    val token: String,
    val category: String,
    val code: String,
    val statusId: Int,
    val tokenFcm: String,
)