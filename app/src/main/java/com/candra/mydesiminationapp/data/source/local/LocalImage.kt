package com.candra.mydesiminationapp.data.source.local

import com.candra.mydesiminationapp.data.source.remote.response.admin.images_dissemination_administrator.ResponseImagesResearchDisseminationAdministratorItem
import com.candra.mydesiminationapp.data.source.remote.response.research_desimination.image.ImageDesiminationLecturerItem

data class LocalImage(
    var image: com.candra.mydesiminationapp.data.source.local.Image,
    var note: String
)
data class Image(
    var filename: String,
    var url: String
)

fun List<ImageDesiminationLecturerItem>.toGenerateImageItem(): MutableList<com.candra.mydesiminationapp.data.source.local.LocalImage>
{
    val listGetImageLecturerItem = mutableListOf<com.candra.mydesiminationapp.data.source.local.LocalImage>()
    this.forEach{listGetImageLecturerItem.add(it.toGetImage())}
    return listGetImageLecturerItem
}

fun ImageDesiminationLecturerItem.toGetImage(): com.candra.mydesiminationapp.data.source.local.LocalImage {
    return com.candra.mydesiminationapp.data.source.local.LocalImage(
        image = com.candra.mydesiminationapp.data.source.local.Image(
            filename = this.image.filename ?: "?", url = this.image.url ?: "?"
        ),
        note = this.note ?: "?"
    )
}

fun List<ResponseImagesResearchDisseminationAdministratorItem>.toGenerateListImageItem():MutableList<LocalImage>
{
    val listImageAdminItem = mutableListOf<LocalImage>()
    this.forEach { listImageAdminItem.add(it.toGetImageItemAdmin()) }
    return listImageAdminItem
}

fun ResponseImagesResearchDisseminationAdministratorItem.toGetImageItemAdmin(): LocalImage{
    return LocalImage(
        image = Image(
            filename = this.image.filename?: "?", url = this.image.url ?: "?"
        ),
        note = this.note ?: "?"
    )
}