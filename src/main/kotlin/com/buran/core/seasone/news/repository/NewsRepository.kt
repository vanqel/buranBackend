package com.buran.core.seasone.news.repository

import com.buran.core.seasone.core.models.SeasonEntity
import com.buran.core.seasone.news.dto.NewsDTO
import com.buran.core.seasone.news.models.NewsEntity
import com.buran.core.seasone.news.models.tables.NewsTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID

@Repository
@Transactional
class NewsRepository: INewsRepository {
    override fun newNews(body: NewsDTO, s: SeasonEntity): NewsEntity {
        return NewsTable.insertAndGetId {
            it[title] = body.title
            it[season] = s.id
            it[date] = LocalDate.now()
            it[text] = body.text
        }.let {
            NewsEntity.findById(it)!!
        }

    }

    override fun getNews(s: SeasonEntity): List<NewsEntity> {
        return NewsEntity.find{NewsTable.season eq s.id }.toList()
    }

    override fun delNews(id: Long): Boolean {
        return NewsTable.deleteWhere { NewsTable.id eq id } == 1
    }

    override fun updateNews(id: Long, body: NewsDTO): NewsEntity {

        NewsTable.update (where = { NewsTable.id eq id }) {
            it[title] = body.title
            it[text] = body.text
        }
        return  NewsEntity.find { NewsTable.id eq id }.first()
    }

    override fun getNewsById(id: Long): NewsEntity {
        return  NewsEntity.find { NewsTable.id eq id }.first()
    }
}
