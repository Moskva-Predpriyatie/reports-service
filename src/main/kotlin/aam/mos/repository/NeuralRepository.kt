package aam.mos.repository

import aam.mos.model.dto.GradientBoostingResponseDto
import aam.mos.model.dto.NeuralNetworkResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.config.*

interface NeuralRepository {

    suspend fun getGradientBoosting(
        orgType: Int,
        personnel: Int,
        equipment: Int,
        district: Int,
        landSquare: Int,
        facilitySquare: Int,
        isLandRental: Int,
        isFacilityRental: Int,
        rentalSquare: Int
    ): NeuralNetworkResponseDto

    suspend fun getNeuralNetwork(
        orgType: Int,
        personnel: Int,
        equipment: Int,
        district: Int,
        landSquare: Int,
        facilitySquare: Int,
        isLandRental: Int,
        isFacilityRental: Int,
        rentalSquare: Int
    ): NeuralNetworkResponseDto
}

class NeuralRepositoryImpl(
    private val httpClient: HttpClient,
    config: ApplicationConfig
) : NeuralRepository {

    private val url = config.property("service.pycuk").getString()

    override suspend fun getNeuralNetwork(
        orgType: Int,
        personnel: Int,
        equipment: Int,
        district: Int,
        landSquare: Int,
        facilitySquare: Int,
        isLandRental: Int,
        isFacilityRental: Int,
        rentalSquare: Int
    ): NeuralNetworkResponseDto =
        httpClient
            .get(url) {
                url {
                    appendPathSegments("getNeuralNetwork")
                    parameters.appendParameters(orgType, personnel, equipment, district, landSquare, facilitySquare, isLandRental, isFacilityRental, rentalSquare)
                }
            }
            .body<NeuralNetworkResponseDto>()

    override suspend fun getGradientBoosting(
        orgType: Int,
        personnel: Int,
        equipment: Int,
        district: Int,
        landSquare: Int,
        facilitySquare: Int,
        isLandRental: Int,
        isFacilityRental: Int,
        rentalSquare: Int
    ): NeuralNetworkResponseDto =
        httpClient
            .get(url) {
                url {
                    appendPathSegments("getGradientBoosting")
                    parameters.appendParameters(orgType, personnel, equipment, district, landSquare, facilitySquare, isLandRental, isFacilityRental, rentalSquare)
                }
            }
            .body<NeuralNetworkResponseDto>()

    private fun ParametersBuilder.appendParameters(
        orgType: Int,
        personnel: Int,
        equipment: Int,
        district: Int,
        landSquare: Int,
        facilitySquare: Int,
        isLandRental: Int,
        isFacilityRental: Int,
        rentalSquare: Int
    ) {
        append("orgType", orgType.toString())
        append("personnel", personnel.toString())
        append("equipment", equipment.toString())
        append("district", district.toString())
        append("landSquare", landSquare.toString())
        append("facilitySquare", facilitySquare.toString())
        append("isLandRental", isLandRental.toString())
        append("isFacilityRental", isFacilityRental.toString())
        append("rentalSquare", rentalSquare.toString())
    }
}