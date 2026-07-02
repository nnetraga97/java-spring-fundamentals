package com.nick.javafundamentals.exercises.spr01;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Thin web layer for {@code SPR-01}: validate input, delegate to the service, map
 * the result to a response. No business rules here.
 *
 * <p>STUB: implement {@link #authorize} so it calls {@link #service} and returns
 * its {@link AuthorizeResponse}. Validation of the request body is already wired
 * via {@code @Valid}; you do not implement that. See {@code AuthorizationControllerTest}.
 */
@RestController
public class AuthorizationController {

    private final AuthorizationApiService service;

    public AuthorizationController(AuthorizationApiService service) {
        this.service = service;
    }

    protected AuthorizationApiService service() {
        return service;
    }

    @PostMapping("/authorizations")
    public AuthorizeResponse authorize(@Valid @RequestBody AuthorizeRequest request) {
        throw new UnsupportedOperationException(
                "TODO SPR-01: delegate to the service and return its response");
    }
}
