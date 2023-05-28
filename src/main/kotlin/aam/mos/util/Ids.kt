package aam.mos.util

import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.id.WrappedObjectId

val <T> Id<T>.objectId: ObjectId get() = (this as WrappedObjectId<T>).id
