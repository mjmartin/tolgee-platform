package io.tolgee.repository

import io.tolgee.model.Organization
import io.tolgee.model.enums.OrganizationRoleType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrganizationRepository : JpaRepository<Organization, Long> {
    fun getOneByAddressPart(addressPart: String): Organization?

    @Query("from Organization o join fetch OrganizationRole r on r.user.id = :userId and r.organization = o")
    fun findAllPermitted(userId: Long?, pageable: Pageable): Page<Array<Any>>

    @Query("from Organization o join fetch OrganizationRole r on r.user.id = :userId and r.organization = o and r.type = :roleType")
    fun findAllPermittedUserRoleType(userId: Long?, pageable: Pageable, roleType: OrganizationRoleType): Page<Array<Any>>

    fun countAllByAddressPart(addressPart: String): Long
}
