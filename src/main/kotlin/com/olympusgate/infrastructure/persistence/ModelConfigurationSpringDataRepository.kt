package com.olympusgate.infrastructure.persistence

import com.olympusgate.infrastructure.persistence.entity.ModelConfigurationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ModelConfigurationSpringDataRepository : JpaRepository<ModelConfigurationEntity, String> {
    fun findByEnabledTrue(): List<ModelConfigurationEntity>

    fun findByProvider(provider: String): List<ModelConfigurationEntity>
}
