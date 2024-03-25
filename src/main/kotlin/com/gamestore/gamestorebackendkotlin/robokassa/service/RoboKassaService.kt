package com.gamestore.gamestorebackendkotlin.balance.robocassa

import com.gamestore.gamestorebackendkotlin.balance.config.RobokassaProps
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.security.MessageDigest
@Service
@EnableConfigurationProperties(RobokassaProps::class)
class RobocassaClient(val props: RobokassaProps) {
    fun generatePaymentLink( outSum: String, description: String, invoiceID: Int): String {
        val signatureValue = generateSignatureValue(props.login, outSum, props.password1, invoiceID)
        val uri = UriComponentsBuilder.fromHttpUrl("https://auth.robokassa.ru/Merchant/Index.aspx")
            .queryParam("MerchantLogin", props.login)
            .queryParam("OutSum", outSum)
            .queryParam("Description", description)
            .queryParam("SignatureValue", signatureValue)
            .queryParam("InvoiceID", invoiceID)
            .build().toUriString()

        return uri
    }

    private fun generateSignatureValue(merchantLogin: String, outSum: String, password: String, invoiceID: Int): String {
        val data = "$merchantLogin:$outSum:$invoiceID::$password"
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(data.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }

}
