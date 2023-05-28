package aam.mos.model.dto

import kotlinx.serialization.Serializable

@Serializable
class TokenDto(
    val token: String,
    val maxAge: Long
)
