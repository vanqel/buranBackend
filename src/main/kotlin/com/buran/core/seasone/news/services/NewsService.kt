package com.buran.core.seasone.news.services

import com.buran.core.auth.errors.ValidationError
import com.buran.core.seasone.core.services.ISeasonService
import com.buran.core.seasone.news.dto.NewsDTO
import com.buran.core.seasone.news.dto.NewsOutput
import com.buran.core.seasone.news.repository.INewsRepository
import com.buran.core.storage.images.dto.CreateImageLink
import com.buran.core.storage.images.service.IStorageImageService
import org.springframework.stereotype.Service
import java.util.*

@Service
class NewsService(
    val seasonService: ISeasonService,
    val images: IStorageImageService,
    val repo: INewsRepository,
) : INewsService {

    private fun getSeasonId(title: String) = seasonService.getSeasonFromTitle(title)

    override fun newNews(obj: NewsDTO, season: String): NewsOutput {
        val seasonId = getSeasonId(season)
        var img = " "
        val news = repo.newNews(obj, seasonId).let { e ->
            img = obj.image.let {
                try {

                    images.putLink(
                        CreateImageLink(
                            e.parentKey,
                            UUID.fromString(it)
                        )
                    )

                } catch (er: Exception) {
                    repo.delNews(e.id.value)
                    throw ValidationError("Не верно заполнен лист изображений")
                }
            }
            e
        }
        return NewsOutput(news.id.value, news.title, news.text, img, news.date)
    }

    override fun delNews(id: Long): Boolean {
        repo.getNewsById(id)?.let {
            images.deleteLinkAll(it.parentKey)
            repo.delNews(id)
            return true
        } ?: throw ValidationError("Новость не найдена")
    }

    override fun updateNews(id: Long, obj: NewsDTO): NewsOutput {
        repo.getNewsById(id)?.let { e ->
            images.deleteLinkAll(
                e.parentKey
            )
            val imgUrl = obj.image.let {
                try {

                    images.putLink(
                        CreateImageLink(
                            e.parentKey,
                            UUID.fromString(it)
                        )

                    )
                } catch (e: Exception) {
                    throw ValidationError("Не верно заполнен лист изображений")
                }
            }
            val result = repo.updateNews(id, obj)
            return NewsOutput(result.id.value, result.title, result.text, imgUrl, result.date)
        } ?: throw ValidationError("Новость не найдена")
    }

    override fun getAllNews(season: String): List<NewsOutput> {
        return repo.getNews(getSeasonId(season)).map {
            NewsOutput(it.id.value, it.title, it.text, images.getListImages(it.parentKey).first(), it.date)
        }
    }

    override fun getNews(id: Long): NewsOutput {
        return repo.getNewsById(id)?.let {
            NewsOutput(it.id.value, it.title, it.text, images.getListImages(it.parentKey).first(), it.date)
        } ?: throw ValidationError("Новость не найдена")
    }
}
