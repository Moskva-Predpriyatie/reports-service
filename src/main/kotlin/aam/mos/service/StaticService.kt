package aam.mos.service

import aam.mos.model.dbo.IndustryDbo
import aam.mos.model.dto.IndustryDto
import aam.mos.repository.StaticRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface StaticService {

    suspend fun getIndustries(): List<IndustryDto>
}

class StaticServiceImpl(
    private val staticRepository: StaticRepository
) : StaticService {

    @Volatile private var industries: List<IndustryDto>? = null
    private val mutex = Mutex()

    override suspend fun getIndustries(): List<IndustryDto> {
        if (industries == null) {
            mutex.withLock {
                if (industries == null) {
                    industries = staticRepository.getIndustries().map(::convertToDto)
                }
            }
        }

        return industries!!
    }

    private fun convertToDto(dbo: IndustryDbo): IndustryDto = IndustryDto(
        id = dbo._id,
        name = dbo.name
    )
}
