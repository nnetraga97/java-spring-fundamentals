package com.nick.javafundamentals.exercises.spr01;

/**
 * The service boundary the controller delegates to (exercise {@code SPR-01}).
 *
 * <p>Keeping this as an interface is the whole point of the exercise: the
 * controller depends on behavior, not on a concrete implementation, and the web
 * slice test can mock it. Business logic lives behind this seam, not in the
 * controller.
 */
public interface AuthorizationApiService {

    AuthorizeResponse authorize(AuthorizeRequest request);
}
