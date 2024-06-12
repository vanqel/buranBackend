package com.buran.core.seasone.core.api.http

import com.buran.core.seasone.RegAPI
import com.buran.core.seasone.core.dto.SeasonCreateInput
import com.buran.core.seasone.core.dto.SeasonOutput
import com.buran.core.seasone.core.services.ISeasonService
import com.buran.core.seasone.core.services.SeasonService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping(RegAPI.SEASONS)
class SeasonController(
    val service: ISeasonService
) {

    @PostMapping
    fun createNewSeasone(
        @RequestParam dateStart: LocalDate,
        @RequestParam dateEnd: LocalDate,
    ): SeasonOutput {
        return service.createSeason(
            SeasonCreateInput(
                dateStart = dateStart,
                dateEnd = dateEnd
            )
        )
    }

    @GetMapping
    fun getListSeasons(): List<SeasonOutput> {
        return service.getList()
    }

    @DeleteMapping
    fun delSeason(
        @RequestParam title: String
    ): Boolean {
        return service.deleteSeason(title)
    }
}
