package com.nick.javafundamentals.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository for {@link AuthorizationRecord} (exercise {@code DATA-04}).
 */
public interface AuthorizationRecordRepository extends JpaRepository<AuthorizationRecord, String> {
}
