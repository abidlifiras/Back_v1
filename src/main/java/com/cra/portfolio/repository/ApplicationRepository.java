package com.cra.portfolio.repository;

import com.cra.portfolio.model.Application;
import com.cra.portfolio.model.Server;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    List<Application> findByAppName(String appName);

    List<Application> findAllByDeletedAtNull(Pageable paging);

    List<Application> findAllByDeletedAtIsNotNull(Pageable paging) ;

    Iterable<Application> findAllByDeletedAtNull();
}
