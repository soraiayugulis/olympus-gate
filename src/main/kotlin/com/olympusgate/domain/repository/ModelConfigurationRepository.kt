package com.olympusgate.domain.repository

import com.olympusgate.domain.model.ModelConfiguration

interface ModelConfigurationRepository {
    fun save(model: ModelConfiguration): ModelConfiguration
    fun findById(id: String): ModelConfiguration?
    fun findAll(): List<ModelConfiguration>
    fun findAllEnabled(): List<ModelConfiguration>
    fun findByProvider(provider: String): List<ModelConfiguration>
    fun deleteById(id: String)
}
