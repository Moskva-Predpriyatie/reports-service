package aam.mos.model.dto

import aam.mos.model.dto.TokenDto
import kotlinx.serialization.Serializable

@Serializable
class TokenPairDto(
    val access: TokenDto,
    val refresh: TokenDto
)
