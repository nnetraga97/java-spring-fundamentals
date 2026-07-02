/**
 * Web / REST API layer exercises.
 *
 * <p>Home for: {@code SPR-01} (clean boundaries), {@code SPR-05} (API versioning),
 * {@code SPR-06} (security filter chain), {@code SPR-08} (JWT resource server),
 * {@code TEST-02} (web slice tests), {@code TEST-04} (error contract),
 * {@code PROD-02} (correlation IDs).
 *
 * <p>Controllers here should stay thin: parse/validate input, delegate to a
 * service, and map results to a stable response. Business rules belong in
 * {@code domain} / service classes so they remain framework-light.
 */
package com.nick.javafundamentals.api;
