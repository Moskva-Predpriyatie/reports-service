@file:UseContextualSerialization(ObjectId::class)

package aam.mos.model.dbo.proj

import aam.mos.model.dbo.ReportDbo
import aam.mos.model.dbo.ReportOutputDbo
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseContextualSerialization
import org.bson.types.ObjectId

@Serializable
data class ReportsProjection(
    val reports: List<ReportDbo> = emptyList()
)
