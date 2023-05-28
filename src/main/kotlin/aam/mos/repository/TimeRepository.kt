package aam.mos.repository

interface TimeRepository {

    fun timestamp(): Long

    fun timestampMillis(): Long
}

class TimeRepositoryImpl : TimeRepository {

    override fun timestamp(): Long =
        System.currentTimeMillis() / 1000L

    override fun timestampMillis(): Long =
        System.currentTimeMillis()
}
