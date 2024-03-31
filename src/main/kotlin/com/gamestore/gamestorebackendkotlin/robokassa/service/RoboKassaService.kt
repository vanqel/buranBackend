package com.gamestore.gamestorebackendkotlin.robokassa.service

import com.gamestore.gamestorebackendkotlin.auth.errors.ValidationError
import com.gamestore.gamestorebackendkotlin.extensions.Result
import com.gamestore.gamestorebackendkotlin.extensions.error
import com.gamestore.gamestorebackendkotlin.extensions.ok
import com.gamestore.gamestorebackendkotlin.products.repository.ProductRepository
import com.gamestore.gamestorebackendkotlin.robokassa.config.RobokassaProps
import com.gamestore.gamestorebackendkotlin.robokassa.repository.KassaRepository
import com.github.michaelbull.result.getOrThrow
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
                            .queryParam("OutSum", "${it.price}.00")
                            .queryParam("Description", it.title)
                            .queryParam("SignatureValue", signatureValue)
                            .queryParam("InvoiceID", invoiceID)
                            .queryParam("IsTest", props.isTest)
                            .build().toUriString()
                    kassaRepository.save(auth.name, productId, invoiceID)
                    println(uri)
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
        val data = "$merchantLogin:$outSum.00:$invoiceID:$password"
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(data.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }



    private fun verify(outSum: Double, invId: Int, signatureValue: String) : Result<Int>? {
        val mrhPass1 = props.password1 // merchant pass1 here
        val outSumm = outSum.toString() // Convert to string
        val invIdStr = invId.toString() // Convert to string
        val param = "${outSumm}0:$invIdStr:$mrhPass1"
        val myCrc =
            MessageDigest.getInstance("MD5").digest(param.toByteArray())
                .joinToString("") { "%02x".format(it) }

        println("CALUlated CRC: $myCrc | Requested CRC: $signatureValue | PARAM-IN: $outSumm:$invIdStr:$mrhPass1 | PARAM-OUT: $param")
        print(myCrc)
        // Compare CRCs
        if (myCrc != signatureValue) {
            return Result.error(ValidationError("Bad sign"))
        }
        return Result.ok(invId)
    }

    fun succ(outSum: Double, invId: Int, signatureValue: String): Result<Boolean> {
        verify(outSum, invId, signatureValue)?.getOrThrow().let {
            kassaRepository.succesPay(invId)
        }
        return Result.ok(true)
    }


}
