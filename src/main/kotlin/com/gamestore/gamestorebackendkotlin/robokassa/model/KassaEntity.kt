package com.gamestore.gamestorebackendkotlin.robokassa.model
import com.gamestore.gamestorebackendkotlin.config.ExtendedLongEntity
import com.gamestore.gamestorebackendkotlin.products.dto.product.ProductOutputDTO
import com.gamestore.gamestorebackendkotlin.robokassa.dto.OperationsOutput
import com.gamestore.gamestorebackendkotlin.robokassa.model.table.KassaTable
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class KassaEntity(id: EntityID<Long>) : ExtendedLongEntity(id, KassaTable) {
    companion object : LongEntityClass<KassaEntity>(KassaTable)

    var user by KassaTable.user
    var product by KassaTable.product
    var status by KassaTable.status
    var individalID by KassaTable.individualID

    fun toDto(
        productOutputDTO: ProductOutputDTO,
        link: String,
    ): OperationsOutput {
        return if (status == true) {
            OperationsOutput(
                invID = individalID,
                product = productOutputDTO,
                status = status,
                link = link,
            )
        } else {
            OperationsOutput(
                invID = individalID,
                product = productOutputDTO,
                status = status,
                link = null,
            )
        }
    }
}
