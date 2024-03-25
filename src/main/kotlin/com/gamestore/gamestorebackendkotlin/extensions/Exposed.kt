package io.dtechs.core.extensions

import io.dtechs.core.auth.ExtendedLongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import org.jetbrains.exposed.sql.transactions.TransactionManager

fun SizedIterable<*>.exists() = this.empty().not()

class BatchInsertUpdateOnDuplicate(
    table: Table,
    val onDupUpdate: List<Column<*>>,
) : BatchInsertStatement(table, false) {
    override fun prepareSQL(
        transaction: Transaction,
        prepared: Boolean,
    ): String {
        val onUpdateSQL =
            if (onDupUpdate.isNotEmpty()) {
                " ON DUPLICATE KEY UPDATE " +
                    onDupUpdate.joinToString {
                        "${transaction.identity(it)}=VALUES(${transaction.identity(it)})"
                    }
            } else {
                ""
            }
        return super.prepareSQL(transaction, prepared) + onUpdateSQL
    }
}

fun <T : Table, E> T.batchInsertOnDuplicateKeyUpdate(
    data: List<E>,
    onDupUpdateColumns: List<Column<*>>,
    body: T.(BatchInsertUpdateOnDuplicate, E) -> Unit,
) {
    data.takeIf { it.isNotEmpty() }?.let {
        val insert = BatchInsertUpdateOnDuplicate(this, onDupUpdateColumns)
        data.forEach {
            insert.addBatch()
            body(insert, it)
        }
        TransactionManager.current().exec(insert)
    }
}

fun <T : Table, E> T.insertOnDuplicateKeyUpdate(
    data: E,
    onDupUpdateColumns: List<Column<*>>,
    body: T.(BatchInsertUpdateOnDuplicate, E) -> Unit,
) {
    val insert = BatchInsertUpdateOnDuplicate(this, onDupUpdateColumns)
    insert.addBatch()
    body(insert, data)
    TransactionManager.current().exec(insert)
}

inline fun ExtendedLongIdTable.selectNotDeleted(where: SqlExpressionBuilder.() -> Op<Boolean>): Query =
    select(SqlExpressionBuilder.where().and { this@selectNotDeleted.deletedAt eq null })

fun ExtendedLongIdTable.selectAllNotDeleted(): Query =
    Query(this, null)
        .andWhere { this@selectAllNotDeleted.deletedAt eq null }
