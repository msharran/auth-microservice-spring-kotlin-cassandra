package com.fireflies.auth_microservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.CassandraType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.util.*


@Table
data class User(
    @JsonIgnore
    var password: String,

    var token: String,

    @CassandraType(type = CassandraType.Name.TEXT)
    var userType: UserType,

    @JsonIgnore
    @CassandraType(type = CassandraType.Name.TEXT)
    var role: Role,

    var userId: UUID,

    @PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    var username: String,

    @JsonIgnore
    var isLoggedIn: Boolean,

    @JsonIgnore
    var isActive: Boolean
)

enum class UserType {
    INFLUENCER, BRAND
}

enum class Role {
    ADMIN, USER
}