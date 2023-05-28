package aam.mos.service

import aam.mos.exception.UnauthorizedException
import aam.mos.model.dbo.FullUserDbo
import aam.mos.model.dto.UserDto
import aam.mos.repository.UserRepository
import aam.mos.util.toBase64
import aam.mos.util.toObjectId
import org.bson.types.ObjectId

interface UserService {

    suspend fun getUser(id: String): UserDto

    suspend fun updateUser(id: String, updates: Map<String, Any>): UserDto
}

class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override suspend fun getUser(id: String): UserDto {
        val user = userRepository.getUser(id.toObjectId()) ?: throw UnauthorizedException()
        return convertToDto(user)
    }

    override suspend fun updateUser(id: String, updates: Map<String, Any>): UserDto {
        val user = userRepository.updateUser(id.toObjectId(), updates) ?: throw UnauthorizedException()
        return convertToDto(user)
    }
    
    private fun convertToDto(dbo: FullUserDbo): UserDto = UserDto(
        id = dbo._id.toBase64(),
        role = dbo.role,
        email = dbo.email,
        name = dbo.name,
        surname = dbo.surname,
        patronymic = dbo.patronymic,
        orgName = dbo.orgName,
        taxId = dbo.taxId,
        orgUrl = dbo.orgUrl,
        orgType = dbo.orgType,
        city = dbo.city,
        position = dbo.position
    )
}
