package com.candra.mydesiminationapp.helper

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.candra.mydesiminationapp.data.source.remote.network.GetTokenLecturer
import com.candra.mydesiminationapp.di.NetworkModule.userStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class TempDataTwo constructor(private val context: Context){
    private val TOKEN_KEY = stringPreferencesKey("token_key")
    private val CATEGORY = stringPreferencesKey("category")
    private val CODE = stringPreferencesKey("code")
    private val STATUS_STUDENT = intPreferencesKey("status_student")
    private val TOKEN_FCM_STUDENT = stringPreferencesKey("token_fcm_student")

    suspend fun getToken() = flow {
        val token = context.userStore.data.first()[TOKEN_KEY]?: ""
        val category = context.userStore.data.first()[CATEGORY]?: ""
        val code = context.userStore.data.first()[CODE]?: ""
        val statusId = context.userStore.data.first()[STATUS_STUDENT]?: 4
        val tokenFcm = context.userStore.data.first()[TOKEN_FCM_STUDENT]?: ""
        emit(GetTokenLecturer(token,category,code,statusId,tokenFcm))
    }

    suspend fun putToken(tokenLecturer: GetTokenLecturer){
        context.userStore.edit {
            it[TOKEN_KEY] = tokenLecturer.token
            it[CATEGORY] = tokenLecturer.category
            it[CODE] = tokenLecturer.code
            it[STATUS_STUDENT] = tokenLecturer.statusId
            it[TOKEN_FCM_STUDENT] = tokenLecturer.tokenFcm
        }
    }
}