package com.nick.javafundamentals.exercises.spr01;

/**
 * Response DTO for {@code POST /authorizations} (exercise {@code SPR-01}).
 *
 * @param authorizationId the id assigned to this authorization
 * @param decision        the outcome, e.g. {@code APPROVED} or {@code DECLINED}
 */
public record AuthorizeResponse(String authorizationId, String decision) {
}
