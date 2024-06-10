package com.buran.core

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BuranCoreAPI

fun main(args: Array<String>) {
    runApplication<BuranCoreAPI>(*args)
}
