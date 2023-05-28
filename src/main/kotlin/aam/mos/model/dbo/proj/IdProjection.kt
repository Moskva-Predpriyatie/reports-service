@file:UseContextualSerialization(ObjectId::class)

package aam.mos.model.dbo.proj

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseContextualSerialization
import org.bson.types.ObjectId
import org.litote.kmongo.Id

@Serializable
data class IdProjection(
    val _id: ObjectId
)
