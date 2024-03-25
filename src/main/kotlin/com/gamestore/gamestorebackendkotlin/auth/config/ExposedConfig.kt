package io.dtechs.core.auth.config

import io.dtechs.core.auth.Tables
import org.jetbrains.exposed.spring.DatabaseInitializer
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.ExperimentalKeywordApi
import org.jetbrains.exposed.sql.Schema
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
class ExposedConfig(
    private val applicationContext: ApplicationContext,
) {
    @Bean
    @OptIn(ExperimentalKeywordApi::class)
    fun databaseConfig() =
        DatabaseConfig {
            useNestedTransactions = false
            defaultSchema = Schema(name = "public")
            preserveKeywordCasing = true
        }

    @EventListener(ApplicationReadyEvent::class)
    fun checkDb() {
        transaction {
            // todo check new exposed version for this stuff
            val missingDbStuff =
                SchemaUtils.statementsRequiredToActualizeScheme(
                    tables = Tables.toTypedArray(),
                    withLogs = false,
                )
            if (missingDbStuff.isNotEmpty()) {
                System.err.println("Missing DB objects!!!")
                missingDbStuff.forEach { System.err.println("$it;\n\\") }
            }
        }
    }

    @Bean
    fun databaseInitializer() =
        DatabaseInitializer(
            applicationContext = applicationContext,
            excludedPackages = listOf(),
        )
}
