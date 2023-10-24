package com.candra.mydesiminationapp.data.source.remote.response.admin.index_dissemination_administrator


import com.candra.mydesiminationapp.data.source.remote.response.admin.index_dissemination_administrator.DeanReportResearchDisseminations
import com.google.gson.annotations.SerializedName

data class ResponseIndexResearchDissemination(
    @SerializedName("dean_report_research_disseminations")
    val deanReportResearchDisseminations: DeanReportResearchDisseminations
)