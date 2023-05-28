package aam.mos.util

import org.bson.types.ObjectId
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun ObjectId.toBase64(): String =
    Base64.UrlSafe.encode(toByteArray())

@OptIn(ExperimentalEncodingApi::class)
fun String.toObjectId(): ObjectId =
    ObjectId(Base64.UrlSafe.decode(this))
