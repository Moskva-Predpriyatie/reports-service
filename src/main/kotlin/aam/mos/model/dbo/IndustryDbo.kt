package aam.mos.model.dbo

import kotlinx.serialization.Serializable

@Serializable
data class IndustryDbo(
    val _id: Int,
    val name: String
)
