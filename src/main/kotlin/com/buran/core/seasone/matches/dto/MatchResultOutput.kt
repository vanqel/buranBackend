package com.buran.core.seasone.matches.dto

import com.buran.core.seasone.matches.enums.FinalResultMatch

data class MatchResultOutput(
    val result: FinalResultMatch,
    val teamScore: Int,
    val enemyScore: Int
)

