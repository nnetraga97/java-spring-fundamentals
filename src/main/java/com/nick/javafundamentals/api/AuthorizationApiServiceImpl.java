package com.nick.javafundamentals.api;

import org.springframework.stereotype.Service;

/**
 * Where the business logic for {@code SPR-01} lives — deliberately separate from
 * the controller so it is testable without the web layer.
 *
 * <p>STUB: implement {@link #authorize}. A reasonable first cut: assign an id and
 * return an {@code APPROVED}/{@code DECLINED} decision based on a simple rule (e.g.
 * decline above a configurable amount — which sets up {@code SPR-02}). The web
 * slice test {@code AuthorizationControllerTest} mocks this service, so it does not
 * force an implementation; the domain unit test you write for {@code TEST-01} is
 * where you would pin this logic down.
 */
@Service
public class AuthorizationApiServiceImpl implements AuthorizationApiService {

    @Override
    public AuthorizeResponse authorize(AuthorizeRequest request) {
        throw new UnsupportedOperationException(
                "TODO SPR-01: assign an id and return an authorization decision");
    }
}
