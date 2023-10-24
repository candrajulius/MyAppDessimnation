package com.candra.mydesiminationapp.data.source.remote.response.admin.index_dissemination_administrator


import com.google.gson.annotations.SerializedName

data class DeanReportResearchDisseminations(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("recapitulations")
    val recapitulations: List<Recapitulation>
)