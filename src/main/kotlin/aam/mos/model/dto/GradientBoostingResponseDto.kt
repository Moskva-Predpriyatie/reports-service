@file:UseContextualSerialization(BigDecimal::class)

package aam.mos.model.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseContextualSerialization
import java.math.BigDecimal

@Serializable
class GradientBoostingResponseDto(
    val from: BigDecimal,
    val to: BigDecimal
)
