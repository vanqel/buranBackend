package com.buran.core.seasone.news.repository

import com.buran.core.seasone.news.dto.NewsDTO
import com.buran.core.seasone.news.models.NewsEntity
import org.jetbrains.exposed.dao.id.EntityID

interface INewsRepository {

    fun newNews(body: NewsDTO, season: EntityID<Long>): NewsEntity

    fun getNews(season: EntityID<Long>): List<NewsEntity>

    fun delNews(id: Long): Boolean

    fun updateNews(id: Long, body: NewsDTO): NewsEntity

    fun getNewsById(id: Long): NewsEntity?

}
