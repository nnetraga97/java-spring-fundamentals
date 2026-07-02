package com.nick.javafundamentals.api;

import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Turns exceptions into the stable {@link ApiError} contract (exercise
 * {@code SPR-01}).
 *
 * <p>STUB: add an {@code @ExceptionHandler} for
 * {@code MethodArgumentNotValidException} that returns HTTP 400 with an
 * {@link ApiError} whose {@code code} is {@code VALIDATION_FAILED} and whose
 * {@code messages} list the field violations. Until you do, validation failures
 * fall through to Spring's default error body and
 * {@code AuthorizationControllerTest} stays red.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    // TODO SPR-01: @ExceptionHandler(MethodArgumentNotValidException.class)
    //   -> ResponseEntity.badRequest().body(new ApiError("VALIDATION_FAILED", messages))
}
