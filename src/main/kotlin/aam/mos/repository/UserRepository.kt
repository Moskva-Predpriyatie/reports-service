package aam.mos.repository

import aam.mos.model.dbo.BaseUserDbo
import aam.mos.model.dbo.FullUserDbo
import aam.mos.model.dbo.SimpleUserDbo
import aam.mos.model.dbo.proj.IdProjection
import aam.mos.model.domain.UserRole
import com.mongodb.client.model.Updates
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineCollection

interface UserRepository {

    suspend fun createUser(email: String): SimpleUserDbo

    suspend fun getUser(id: ObjectId): FullUserDbo?

    suspend fun getSimpleUserByEmail(email: String): SimpleUserDbo?

    suspend fun updateUser(id: ObjectId, updates: Map<String, Any>): FullUserDbo?

    suspend fun deleteUser(id: ObjectId)
}

class UserRepositoryImpl(
    private val userCollection: CoroutineCollection<FullUserDbo>
) : UserRepository {

    private val simpleUserCollection: CoroutineCollection<SimpleUserDbo> = userCollection.withDocumentClass()

    override suspend fun createUser(email: String): SimpleUserDbo {
        val newUser = SimpleUserDbo(
            role = UserRole.USER.num,
            email = email,
        )

        simpleUserCollection.insertOne(newUser)

        return newUser
    }

    override suspend fun updateUser(id: ObjectId, updates: Map<String, Any>): FullUserDbo? {
        val sets = updates.map { (name, value) -> Updates.set(name, value) }
        return userCollection.findOneAndUpdate(BaseUserDbo::_id eq id, combine(sets))
    }

    override suspend fun getUser(id: ObjectId): FullUserDbo? =
        userCollection.findOne(BaseUserDbo::_id eq id)

    override suspend fun getSimpleUserByEmail(email: String): SimpleUserDbo? =
        simpleUserCollection
            .find(BaseUserDbo::email eq email)
            .projection(BaseUserDbo::_id, BaseUserDbo::role, BaseUserDbo::email, BaseUserDbo::name)
            .limit(1)
            .first()

    override suspend fun deleteUser(id: ObjectId) {
        userCollection.deleteOne(BaseUserDbo::_id eq id)
    }
}
