package com.olympusgate.infrastructure.persistence

import com.olympusgate.domain.model.ModelConfiguration
import com.olympusgate.domain.repository.ModelConfigurationRepository
import com.olympusgate.infrastructure.persistence.entity.ModelConfigurationEntity
import org.springframework.stereotype.Repository

@Repository
class ModelConfigurationRepositoryImpl(
    private val jpaRepository: ModelConfigurationSpringDataRepository,
) : ModelConfigurationRepository {
    override fun save(model: ModelConfiguration): ModelConfiguration {
        val entity = toEntity(model)
        val saved = jpaRepository.save(entity)
        return toDomain(saved)
    }

    override fun findById(id: String): ModelConfiguration? {
        return jpaRepository.findById(id).map { toDomain(it) }.orElse(null)
    }

    override fun findAll(): List<ModelConfiguration> {
        return jpaRepository.findAll().map { toDomain(it) }
    }

    override fun findAllEnabled(): List<ModelConfiguration> {
        return jpaRepository.findByEnabledTrue().map { toDomain(it) }
    }

    override fun findByProvider(provider: String): List<ModelConfiguration> {
        return jpaRepository.findByProvider(provider).map { toDomain(it) }
    }

    override fun deleteById(id: String) {
        jpaRepository.deleteById(id)
    }

    private fun toEntity(model: ModelConfiguration): ModelConfigurationEntity {
        return ModelConfigurationEntity(
            id = model.id,
            name = model.name,
            provider = model.provider,
            costPer1kTokens = model.costPer1kTokens,
            maxTokens = model.maxTokens,
            capabilities = model.capabilities.joinToString(","),
            enabled = model.enabled,
        )
    }

    private fun toDomain(entity: ModelConfigurationEntity): ModelConfiguration {
        return ModelConfiguration(
            id = entity.id,
            name = entity.name,
            provider = entity.provider,
            costPer1kTokens = entity.costPer1kTokens,
            maxTokens = entity.maxTokens,
            capabilities = entity.capabilities.split(","),
            enabled = entity.enabled,
        )
    }
}
