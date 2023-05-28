package aam.mos.repository

import aam.mos.model.dbo.IndustryDbo
import org.litote.kmongo.coroutine.CoroutineCollection

interface StaticRepository {

    suspend fun getIndustries(): List<IndustryDbo>
}

class StaticRepositoryImpl(
    private val industriesCollection: CoroutineCollection<IndustryDbo>
) : StaticRepository {

    override suspend fun getIndustries(): List<IndustryDbo> =
        industriesCollection.find().toList()
}
