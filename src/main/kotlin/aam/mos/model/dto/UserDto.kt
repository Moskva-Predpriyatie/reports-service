package aam.mos.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val role: Int,
    val email: String,
    val name: String? = null,
    val surname: String? = null,
    val patronymic: String? = null,
    val orgName: String? = null,
    val isIndividual: Boolean? = null,
    val taxId: String? = null,
    val orgUrl: String? = null,
    val orgType: Int? = null,
    val city: String? = null,
    val position: String? = null
)
