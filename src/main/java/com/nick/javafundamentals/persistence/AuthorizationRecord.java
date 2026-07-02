package com.nick.javafundamentals.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Persisted authorization row (exercise {@code DATA-04}).
 *
 * <p>STUB: this entity is missing the piece that prevents lost updates. Add a
 * {@code @jakarta.persistence.Version} field (e.g. {@code long version}) so
 * Hibernate checks the version on update and raises an optimistic-lock failure
 * when two callers race. {@code AuthorizationRecordOptimisticLockTest} stays red
 * until you do.
 */
@Entity
public class AuthorizationRecord {

    @Id
    private String id;

    private String status;

    // TODO DATA-04: add an @Version field here.

    protected AuthorizationRecord() {
        // required by JPA
    }

    public AuthorizationRecord(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
