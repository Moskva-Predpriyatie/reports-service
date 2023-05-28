@file:UseContextualSerialization(ObjectId::class)

package aam.mos.model.dbo

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseContextualSerialization
import org.bson.types.ObjectId

abstract class BaseUserDbo {

    abstract val _id: ObjectId
    abstract val role: Int
    abstract val email: String
    abstract val name: String?

    val isFull: Boolean get() = name != null
}

@Serializable
data class FullUserDbo(
    override val _id: ObjectId = ObjectId.get(),
    override val role: Int,
    override val email: String,
    override val name: String?,
    val surname: String?,
    val patronymic: String?,
    val orgName: String?,
    val taxId: String?,
    val orgUrl: String?,
    val orgType: Int?,
    val city: String?,
    val position: String?,
    val reports: List<ReportDbo>?
) : BaseUserDbo()

@Serializable
data class SimpleUserDbo(
    override val _id: ObjectId = ObjectId.get(),
    override val role: Int,
    override val email: String,
    override val name: String? = null
) : BaseUserDbo()
