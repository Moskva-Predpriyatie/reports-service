@file:UseContextualSerialization(BigDecimal::class)

package aam.mos.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseContextualSerialization
import java.math.BigDecimal

@Serializable
data class ReportResultDto(
    val id: String? = null,
    val request: ReportRequestDto,
    val from: BigDecimal,
    val to: BigDecimal,
    val equipmentCost: BigDecimal? = null,
    val facilityCost: BigDecimal? = null,
    val landCost: BigDecimal? = null,
    val rentalCost: BigDecimal? = null,
    val salaryCost: BigDecimal? = null,
    val insuranceCost: BigDecimal? = null
)

@Serializable
data class ReportRequestDto(
    val personnel: Int,
    val district: String,
    @SerialName("branch") val orgType: Int,
    val isIndividual: Boolean,
    val landSquare: Int,
    val facilitySquare: Int,
    val equipment: Int,
    val isLandRental: Boolean,
    val isFacilityRental: Boolean,
    val rentalSquare: Int? = null
)
