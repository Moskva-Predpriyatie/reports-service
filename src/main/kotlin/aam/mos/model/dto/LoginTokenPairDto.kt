package aam.mos.model.dto

import kotlinx.serialization.Serializable

@Serializable
class LoginTokenPairDto(
    val isRegistered: Boolean,
    val access: TokenDto,
    val refresh: TokenDto
)
