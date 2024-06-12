package com.buran.core.storage.images.service

import com.buran.core.auth.errors.GeneralError
import com.buran.core.auth.errors.ValidationError
import com.buran.core.storage.images.dto.CreateImageLink
import com.buran.core.storage.images.repository.IStorageImageRepository
import com.buran.core.storage.core.service.MinioService
import org.springframework.stereotype.Service
import java.util.*

@Service
class StorageImageService(
    val repo: IStorageImageRepository,
    val minioService: MinioService,
) : IStorageImageService {
    override fun putLink(image: CreateImageLink): String {
        val find = repo.getLink(image)
        return if ( find == null){
                try {
                    val url = minioService.getObject(image.image)?.url
                    if (url != null) {
                        repo.addLink(image)
                        url
                    } else throw ValidationError("Изображение не найдено")
                } catch (e: Exception) {
                    throw GeneralError("Ошибка загрузки изображения")
                }
            } else minioService.getObject(image.image)!!.url!!

    }

    override fun getListImages(parentKey: UUID): List<String?> {
        return try {
            repo.getListImages(parentKey).filterNotNull().map {
                minioService.getObject(it.id.value)?.url
            }.ifEmpty { throw ValidationError("Изображение не найдено") }
        } catch (e: Exception) {
            throw GeneralError("Ошибка загрузки изображения")
        }
    }

    override fun deleteLink(image: CreateImageLink): Boolean {
        return try {
            val url = minioService.delObject(image.image.toString())
            if (url) {
                repo.deleteLink(image)
            } else throw ValidationError("Изображение не найдено")
        } catch (e: Exception) {
            throw GeneralError("Ошибка загрузки изображения")
        }
    }

    override fun deleteLinkAll(uuid: UUID): Boolean {
        try {
            repo.getListImages(uuid).filterNotNull().forEach {
                minioService.delObject(it.id.value.toString())
            }
            return repo.deleteLinkAll(uuid)
        } catch (e: Exception){
            throw GeneralError("Ошибка очистки изображений")
        }

    }

}
