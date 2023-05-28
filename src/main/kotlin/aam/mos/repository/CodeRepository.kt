package aam.mos.repository

import kotlin.random.Random

interface CodeRepository {

    fun createCode(size: Int): String

    fun createDigitCode(size: Int): String
}

class CodeRepositoryImpl(
    private val random: Random
) : CodeRepository {

    private val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    private val powers = intArrayOf(1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000)

    override fun createCode(size: Int): String =
        String(CharArray(size) { allowedChars[random.nextInt(allowedChars.size)] })

    override fun createDigitCode(size: Int): String =
        random.nextInt(powers[size - 1], powers[size]).toString()
}
