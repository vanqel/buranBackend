package com.buran.core.seasone.news.api.http

import com.buran.core.seasone.RegAPI
import com.buran.core.seasone.news.dto.NewsDTO
import com.buran.core.seasone.news.dto.NewsOutput
import com.buran.core.seasone.news.services.INewsService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class NewsController(
    val service: INewsService
) {

    @GetMapping("${RegAPI.NEWS_SEASON}/{id_news}")
    fun getNews(
        @PathVariable season: String,
        @PathVariable id_news: Long,
    ): NewsOutput {
        return service.getNews(id_news)
    }

    @GetMapping(RegAPI.NEWS_SEASON)
    fun getAllNews(
        @PathVariable season: String
    ): List<NewsOutput> {
        return service.getAllNews(season)
    }

    @PostMapping(RegAPI.NEWS_SEASON)
    fun postNews(
        @PathVariable season: String,
        @RequestBody body: NewsDTO,
    ): NewsOutput {
        return service.newNews(body, season)
    }

    @DeleteMapping("${RegAPI.NEWS_SEASON}/{id_news}")
    fun delNews(
        @PathVariable season: String,
        @PathVariable id_news: Long,
    ): Boolean {
        return service.delNews(id_news)
    }
}

