package com.buran.core.seasone.news.repository

import com.buran.core.seasone.core.models.SeasonEntity
import com.buran.core.seasone.news.dto.NewsDTO
import com.buran.core.seasone.news.models.NewsEntity

interface INewsRepository {

    fun newNews(body: NewsDTO, season: SeasonEntity): NewsEntity

    fun getNews(season: SeasonEntity): List<NewsEntity>

    fun delNews(id: Long): Boolean

    fun updateNews(id: Long, body: NewsDTO): NewsEntity

    fun getNewsById(id: Long): NewsEntity?

}
