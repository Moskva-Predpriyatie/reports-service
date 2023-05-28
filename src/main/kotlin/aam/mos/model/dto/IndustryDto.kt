package aam.mos.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class IndustryDto(
    val id: Int,
    val name: String
)
