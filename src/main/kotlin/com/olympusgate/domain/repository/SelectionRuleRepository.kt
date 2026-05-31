package com.olympusgate.domain.repository

import com.olympusgate.domain.model.SelectionRule

interface SelectionRuleRepository {
    fun save(rule: SelectionRule): SelectionRule

    fun findById(id: String): SelectionRule?

    fun findAll(): List<SelectionRule>

    fun findAllEnabled(): List<SelectionRule>

    fun findByPriority(priority: Int): List<SelectionRule>

    fun deleteById(id: String)
}
