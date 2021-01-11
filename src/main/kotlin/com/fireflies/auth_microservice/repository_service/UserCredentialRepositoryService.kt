package com.fireflies.auth_microservice.repository_service

import com.fireflies.auth_microservice.model.User
import com.fireflies.auth_microservice.util.context
import com.fireflies.auth_microservice.util.log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CoroutineCrudRepository<User, String> {
    /* Only Partition Key and Clustering key field queries allowed */
    suspend fun findByUsername(username: String): User?
}

@Component
class UserService {
    val repository: UserRepository get() = context.getBean()

    suspend fun findActiveUser(username: String): User? {
        val user = repository.findByUsername(username) ?: return null
        return if (user.isActive) user else null
    }

    suspend fun logout(user: User) : User {
        user.isLoggedIn = false
        return repository.save(user)
    }

    suspend fun login(user: User, token: String): User {
        user.token = token
        user.isLoggedIn = true
        return repository.save(user)
    }
}