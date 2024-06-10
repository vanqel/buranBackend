package com.buran.core.storage.images.repository

import com.buran.core.storage.images.dto.CreateImageLink
import com.buran.core.storage.images.models.ImageEntity
import java.util.UUID

interface IStorageImageRepository {
    fun addLink(image: CreateImageLink): ImageEntity

    fun getLink(image: CreateImageLink): ImageEntity?

    fun getListImages(parentKey: UUID): List<ImageEntity?>

    fun deleteLink(image: CreateImageLink): Boolean

    fun deleteLinkAll(uuid: UUID): Boolean

}
