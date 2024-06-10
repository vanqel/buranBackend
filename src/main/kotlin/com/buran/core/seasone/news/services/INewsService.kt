package com.buran.core.seasone.news.services

import com.buran.core.seasone.news.dto.NewsDTO
import com.buran.core.seasone.news.dto.NewsOutput

interface INewsService {
    fun newNews(obj: NewsDTO, season: String): NewsOutput
    fun delNews(id: Long): Boolean
    fun updateNews(id: Long, obj: NewsDTO): NewsOutput
    fun getAllNews(season: String): List<NewsOutput>
    fun getNews(id: Long): NewsOutput
}
