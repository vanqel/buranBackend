package com.gamestore.gamestorebackendkotlin.auth.dto.authorization

data class FrmrInfoInput(
    val frmrRole: String,
    val frmrCountry: String,
    val frmrDepartment: String,
) {
    fun toClaims(): Map<String, String> {
        return mapOf(
            "frmrRole" to frmrRole,
            "frmrCountry" to frmrCountry,
            "frmrDepartment" to frmrDepartment,
        )
    }
}
