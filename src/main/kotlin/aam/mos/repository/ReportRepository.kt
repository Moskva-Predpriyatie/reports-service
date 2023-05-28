package aam.mos.repository

import aam.mos.model.dbo.FullUserDbo
import aam.mos.model.dbo.ReportDbo
import aam.mos.model.dbo.proj.ReportsProjection
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq
import org.litote.kmongo.push

interface ReportRepository {

    suspend fun addReport(id: ObjectId, report: ReportDbo): Boolean

    suspend fun getReports(id: ObjectId): List<ReportDbo>?

    suspend fun getReport(id: ObjectId, reportId: ObjectId): ReportDbo?
}

class ReportRepositoryImpl(
    private val userCollection: CoroutineCollection<FullUserDbo>
) : ReportRepository {

    override suspend fun addReport(id: ObjectId, report: ReportDbo) =
        userCollection.updateOne(FullUserDbo::_id eq id, push(FullUserDbo::reports, report)).modifiedCount > 0

    override suspend fun getReports(id: ObjectId): List<ReportDbo>? =
        userCollection.findAndCast<ReportsProjection>(FullUserDbo::_id eq id)
            .projection(FullUserDbo::reports)
            .first()
            ?.reports

    override suspend fun getReport(id: ObjectId, reportId: ObjectId): ReportDbo? =
        getReports(id)?.find { it._id == reportId }
}
