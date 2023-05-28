package aam.mos.util

import aam.mos.exception.ValidationException
import io.konform.validation.Invalid
import io.konform.validation.ValidationBuilder
import io.konform.validation.ValidationResult
import io.konform.validation.jsonschema.maximum
import io.konform.validation.jsonschema.minimum
import io.konform.validation.jsonschema.pattern

private val EMAIL_REGEX = "^[a-zA-Z\\d+._%\\-]{1,64}@[a-zA-Z\\d][a-zA-Z\\d\\-]{0,32}(\\.[a-zA-Z\\d][a-zA-Z\\d\\-]{0,16})+\$".toRegex()
private val ETH_REGEX = "^0x[a-fA-F\\d]{40}\$".toRegex()
private val ALPHANUMERIC_REGEX = "^\\w*\$".toRegex()
private val NUMERIC_REGEX = "^\\d*\$".toRegex()

fun <T> ValidationResult<T>.throwIfInvalid() {
    if (this is Invalid) throw ValidationException(toString())
}

fun ValidationBuilder<String>.email() {
    pattern(EMAIL_REGEX)
}

fun ValidationBuilder<String>.ethAddress() {
    pattern(ETH_REGEX)
}

fun ValidationBuilder<String>.length(length: Int) {
    addConstraint("must have {0} characters", length.toString()) { it.length == length }
}

fun ValidationBuilder<String>.alphanumeric() {
    pattern(ALPHANUMERIC_REGEX)
}

fun ValidationBuilder<String>.numeric() {
    pattern(NUMERIC_REGEX)
}

fun ValidationBuilder<Int>.digit() {
    minimum(0)
    maximum(9)
}

fun ValidationBuilder<Int>.positive() {
    minimum(0)
}

fun ValidationBuilder<String>.id() {
    length(16)
}