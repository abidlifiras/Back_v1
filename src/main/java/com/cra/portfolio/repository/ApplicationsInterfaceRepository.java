package com.cra.portfolio.repository;

import com.cra.portfolio.model.ApplicationsInterface;
import com.cra.portfolio.model.ApplicationsKey;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationsInterfaceRepository extends JpaRepository<ApplicationsInterface, ApplicationsKey> {

    /*@Transactional
    default ApplicationsInterface merge(ApplicationsInterface applicationsInterface) {
        return this.save(applicationsInterface);
    }*/


       /* @Modifying
        @Query("update ApplicationsInterface ai set ai.applicationSrc.id = :newSrcId, ai.applicationTarget.id = :newTargetId where ai.id.applicationSrcId = :srcId and ai.id.applicationTargetId = :targetId")
        int updateAppInterface(@Param("srcId") Integer srcId, @Param("targetId") Integer targetId, @Param("newSrcId") Integer newSrcId, @Param("newTargetId") Integer newTargetId);*/

List<ApplicationsInterface>findAllByDeletedAtNull(Pageable paging);
}
