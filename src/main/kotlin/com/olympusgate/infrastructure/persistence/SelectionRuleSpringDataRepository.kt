package com.olympusgate.infrastructure.persistence

import com.olympusgate.infrastructure.persistence.entity.SelectionRuleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SelectionRuleSpringDataRepository : JpaRepository<SelectionRuleEntity, String> {
    fun findByEnabledTrue(): List<SelectionRuleEntity>

    fun findByPriority(priority: Int): List<SelectionRuleEntity>
}
