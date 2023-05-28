package aam.mos.service

import aam.mos.controller.ReportController
import aam.mos.model.dto.GradientBoostingResponseDto
import aam.mos.model.dto.NeuralNetworkResponseDto
import aam.mos.repository.NeuralRepository

interface NeuralService {

    suspend fun getGradientBoosting(
        reportPostBody: ReportController.ReportPostBody
    ): NeuralNetworkResponseDto

    suspend fun getNeuralNetwork(
        reportPostBody: ReportController.ReportPostBody
    ): NeuralNetworkResponseDto
}

class NeuralServiceImpl(
    private val neuralRepository: NeuralRepository
) : NeuralService {

    private val districts = mapOf(
        "ВАО" to 0, "ЗАО" to 1, "ЗелАО" to 2,
        "НАО" to 3, "САО" to 4, "СВАО" to 5,
        "СЗАО" to 6, "ТАО" to 7, "ЦАО" to 8,
        "ЮАО" to 9, "ЮВАО" to 10, "ЮЗАО" to 11
    )

    override suspend fun getGradientBoosting(reportPostBody: ReportController.ReportPostBody): NeuralNetworkResponseDto {
        return neuralRepository.getGradientBoosting(
            orgType = reportPostBody.orgType,
            personnel = reportPostBody.personnel,
            equipment = reportPostBody.equipment,
            district = requireNotNull(districts[reportPostBody.district]),
            landSquare = reportPostBody.landSquare,
            facilitySquare = reportPostBody.facilitySquare,
            isLandRental = reportPostBody.isLandRental.intValue,
            isFacilityRental = reportPostBody.isFacilityRental.intValue,
            rentalSquare = reportPostBody.rentalSquare ?: 0
        )
    }

    override suspend fun getNeuralNetwork(reportPostBody: ReportController.ReportPostBody): NeuralNetworkResponseDto {
        return neuralRepository.getNeuralNetwork(
            orgType = reportPostBody.orgType,
            personnel = reportPostBody.personnel,
            equipment = reportPostBody.equipment,
            district = requireNotNull(districts[reportPostBody.district]),
            landSquare = reportPostBody.landSquare,
            facilitySquare = reportPostBody.facilitySquare,
            isLandRental = reportPostBody.isLandRental.intValue,
            isFacilityRental = reportPostBody.isFacilityRental.intValue,
            rentalSquare = reportPostBody.rentalSquare ?: 0
        )
    }

    private val Boolean.intValue
        get() = if (this) 1 else 0
}