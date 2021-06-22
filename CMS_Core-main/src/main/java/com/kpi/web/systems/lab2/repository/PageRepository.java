package com.kpi.web.systems.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kpi.web.systems.lab2.model.Page;

import java.util.Optional;

public interface PageRepository extends JpaRepository<Page, Long> {

    Optional<Page> findByCode(String code);
}
