package com.fireflies.auth_microservice.configuration

import com.fireflies.auth_microservice.AppProperties
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration
import org.springframework.data.cassandra.config.SchemaAction
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext
import org.springframework.data.cassandra.core.mapping.NamingStrategy
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories
import java.util.*


@Configuration
@EnableReactiveCassandraRepositories
class CassandraConfiguration : AbstractReactiveCassandraConfiguration() {

    override fun cassandraMapping(): CassandraMappingContext {
        val cassandraMapping = super.cassandraMapping()
        cassandraMapping.setNamingStrategy(NamingStrategy.SNAKE_CASE.transform(String::toUpperCase))
        return cassandraMapping
    }

    override fun getSchemaAction(): SchemaAction {
        return SchemaAction.CREATE_IF_NOT_EXISTS
    }

    override fun getKeyspaceName(): String {
        return AppProperties.Cassandra.KEYSPACE_NAME
    }

    override fun getContactPoints(): String {
        return AppProperties.Cassandra.NODES
    }

    override fun getEntityBasePackages(): Array<String> {
        return AppProperties.Cassandra.MODELS
    }

    override fun getKeyspaceCreations(): List<CreateKeyspaceSpecification> {
        val createKeyspaceSpecifications: MutableList<CreateKeyspaceSpecification> = ArrayList()
        createKeyspaceSpecifications.add(keySpaceSpecification)
        return createKeyspaceSpecifications
    }

    // Below method creates KeySpace if it doesn't exist.
    private val keySpaceSpecification: CreateKeyspaceSpecification
        get() {
            return CreateKeyspaceSpecification
                .createKeyspace(AppProperties.Cassandra.KEYSPACE_NAME)
                .ifNotExists()
                .withSimpleReplication(AppProperties.Cassandra.REPLICATION_FACTOR)
        }
}