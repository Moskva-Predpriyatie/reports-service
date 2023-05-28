package aam.mos.service

import aam.mos.controller.ReportController
import aam.mos.exception.NotFoundException
import aam.mos.exception.UnauthorizedException
import aam.mos.model.dbo.ReportDbo
import aam.mos.model.dbo.ReportInputDbo
import aam.mos.model.dbo.ReportOutputDbo
import aam.mos.model.dto.ReportRequestDto
import aam.mos.model.dto.ReportResultDto
import aam.mos.repository.ReportRepository
import aam.mos.util.toBase64
import aam.mos.util.toObjectId
import org.bson.types.ObjectId

interface ReportService {

    suspend fun createReport(id: String?, reportInput: ReportController.ReportPostBody): ReportResultDto

    suspend fun getReports(id: String): List<ReportResultDto>

    suspend fun getReport(id: String, reportId: String): ReportResultDto
}

class ReportServiceImpl(
    private val reportRepository: ReportRepository,
    private val neuralService: NeuralService
) : ReportService {

    override suspend fun createReport(id: String?, reportInput: ReportController.ReportPostBody): ReportResultDto {
        val output = neuralService.getGradientBoosting(reportInput)
        if (id == null) {
            return ReportResultDto(null, reportInput.toResponseDto(), output.from, output.to)
        } else {
            val reportDbo = ReportDbo(
                _id = ObjectId.get(),
                fileId = null,
                input = ReportInputDbo(
                    personnel = reportInput.personnel,
                    district = reportInput.district,
                    orgType = reportInput.orgType,
                    isIndividual = reportInput.isIndividual,
                    landSquare = reportInput.landSquare,
                    facilitySquare = reportInput.facilitySquare,
                    equipment = reportInput.equipment,
                    isLandRental = reportInput.isLandRental,
                    isFacilityRental = reportInput.isFacilityRental,
                    rentalSquare = reportInput.rentalSquare,
                ),
                output = ReportOutputDbo(
                    equipmentCost = output.equipmentCost,
                    facilityCost = output.facilityCost,
                    landCost = output.landCost,
                    rentalCost = output.rentalCost,
                    salaryCost = output.salaryCost,
                    insuranceCost = output.insuranceCost,
                    from = output.from,
                    to = output.to,
                )
            )
            val isAdded = reportRepository.addReport(id.toObjectId(), reportDbo)
            if (!isAdded) throw UnauthorizedException()
            return ReportResultDto(
                id = reportDbo._id.toBase64(),
                request = reportInput.toResponseDto(),
                from = output.from,
                to = output.to,
                equipmentCost = output.equipmentCost,
                facilityCost = output.facilityCost,
                landCost = output.landCost,
                rentalCost = output.rentalCost,
                salaryCost = output.salaryCost,
                insuranceCost = output.insuranceCost,
            )
        }
    }

    override suspend fun getReports(id: String): List<ReportResultDto> {
        val reports = reportRepository.getReports(id.toObjectId()) ?: throw UnauthorizedException()
        return reports.map { report ->
            ReportResultDto(
                id = report._id.toBase64(),
                request = report.input.toResponseDto(),
                from = report.output.from,
                to = report.output.to,
                equipmentCost = report.output.equipmentCost,
                facilityCost = report.output.facilityCost,
                landCost = report.output.landCost,
                rentalCost = report.output.rentalCost,
                salaryCost = report.output.salaryCost,
                insuranceCost = report.output.insuranceCost,
            )
        }
    }

    override suspend fun getReport(id: String, reportId: String): ReportResultDto {
        val report = reportRepository.getReport(id.toObjectId(), reportId.toObjectId()) ?: throw NotFoundException()
        return ReportResultDto(
            id = report._id.toBase64(),
            request = report.input.toResponseDto(),
            from = report.output.from,
            to = report.output.to,
            equipmentCost = report.output.equipmentCost,
            facilityCost = report.output.facilityCost,
            landCost = report.output.landCost,
            rentalCost = report.output.rentalCost,
            salaryCost = report.output.salaryCost,
            insuranceCost = report.output.insuranceCost,
        )
    }

    private fun ReportInputDbo.toResponseDto(): ReportRequestDto =
        ReportRequestDto(personnel, district, orgType, isIndividual, landSquare, facilitySquare, equipment, isLandRental, isFacilityRental)

    private fun ReportController.ReportPostBody.toResponseDto(): ReportRequestDto =
        ReportRequestDto(personnel, district, orgType, isIndividual, landSquare, facilitySquare, equipment, isLandRental, isFacilityRental)
}