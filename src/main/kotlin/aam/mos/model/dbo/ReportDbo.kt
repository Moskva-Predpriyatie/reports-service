@file:UseContextualSerialization(ObjectId::class, BigDecimal::class)

package aam.mos.model.dbo

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseContextualSerialization
import org.bson.types.ObjectId
import java.math.BigDecimal

@Serializable
class ReportDbo(
    val _id: ObjectId,
    val fileId: ObjectId?,
    val input: ReportInputDbo,
    val output: ReportOutputDbo
)

@Serializable
class ReportInputDbo(
    val personnel: Int,
    val district: String,
    val orgType: Int,
    val isIndividual: Boolean,
    val landSquare: Int,
    val facilitySquare: Int,
    val equipment: Int,
    val isLandRental: Boolean,
    val isFacilityRental: Boolean,
    val rentalSquare: Int?
)

@Serializable
class ReportOutputDbo(
    val equipmentCost: BigDecimal,
    val facilityCost: BigDecimal,
    val landCost: BigDecimal,
    val rentalCost: BigDecimal,
    val salaryCost: BigDecimal,
    val insuranceCost: BigDecimal,
    val from: BigDecimal,
    val to: BigDecimal
)
