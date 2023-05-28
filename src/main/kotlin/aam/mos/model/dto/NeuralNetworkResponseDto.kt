@file:UseContextualSerialization(BigDecimal::class)

package aam.mos.model.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseContextualSerialization
import java.math.BigDecimal

@Serializable
class NeuralNetworkResponseDto(
    val equipmentCost: BigDecimal,
    val facilityCost: BigDecimal,
    val landCost: BigDecimal,
    val rentalCost: BigDecimal,
    val salaryCost: BigDecimal,
    val insuranceCost: BigDecimal,
    val from: BigDecimal,
    val to: BigDecimal
)
