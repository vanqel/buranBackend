package com.gamestore.gamestorebackendkotlin.robokassa.service

import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.extensions.Result
import com.gamestore.gamestorebackendkotlin.extensions.error
import com.gamestore.gamestorebackendkotlin.extensions.ok
import com.gamestore.gamestorebackendkotlin.products.repository.ProductRepository
import com.gamestore.gamestorebackendkotlin.robokassa.config.RobokassaProps
import com.gamestore.gamestorebackendkotlin.robokassa.repository.KassaRepository
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder
import java.security.MessageDigest

@Service
@EnableConfigurationProperties(RobokassaProps::class)
class RoboKassaService(val props: RobokassaProps, val kassaRepository: KassaRepository, val productRepository: ProductRepository) {
    fun generatePaymentLink(productId: Long): Result<String> {
        val invoiceID = (Math.random() * 100000 + 1).toInt()
        return SecurityContextHolder.getContext()?.let { authHolder ->
            authHolder.authentication.let {
                    auth ->
                println(auth.name)
                productRepository.findByProductID(productId)?.let {
                    val signatureValue = generateSignatureValue(props.login, it.price, props.password1, invoiceID)
                    val uri =
                        UriComponentsBuilder.fromHttpUrl("https://auth.robokassa.ru/Merchant/Index.aspx")
                            .queryParam("MerchantLogin", props.login)
                            .queryParam("OutSum", it.price)
                            .queryParam("Description", it.title)
                            .queryParam("SignatureValue", signatureValue)
                            .queryParam("InvoiceID", invoiceID)
                            .queryParam("IsTest", props.isTest)
                            .build().toUriString()
                    kassaRepository.save(auth.name, productId, invoiceID)
                    Result.ok(uri)
                }
            }
        } ?: Result.error(ValidationError("Пользователь не авторизован"))
    }

    private fun generateSignatureValue(
        merchantLogin: String,
        outSum: Long,
        password: String,
        invoiceID: Int,
    ): String {
        val data = "$merchantLogin:$outSum:$invoiceID:$password"
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(data.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
}
