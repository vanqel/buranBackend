package com.buran.core.seasone.statistic.models

import org.jetbrains.exposed.sql.Table

object ManualTable: Table("manual_table") {
    val n = long("n")
    val i = long("i")
    val v = long("v")
    val vo = long("vo")
    val vb = long("vb")
    val pb = long("pb")
    val po = long("po")
    val p = long("p")
    val sh = varchar("sh", 50)
    val o = long("o")
    val pero = double("pero")
}
