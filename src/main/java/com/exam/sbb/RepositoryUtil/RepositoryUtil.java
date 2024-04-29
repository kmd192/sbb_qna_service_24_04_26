package com.exam.sbb.RepositoryUtil;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RepositoryUtil {

    @Transactional
    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
    void disableForeginKeyChecks();

    @Transactional
    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)
    void enableForeginKeyChecks();
}
