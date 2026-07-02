package com.nick.javafundamentals.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Spec for {@code SPR-01} (web layer only). Remove {@code @Disabled}, then:
 * <ol>
 *   <li>implement {@link AuthorizationController#authorize} (makes the success test pass),</li>
 *   <li>implement the validation handler in {@link ApiExceptionHandler} (makes the
 *       validation test pass).</li>
 * </ol>
 */
@Disabled("SPR-01 — remove this line to begin the exercise")
@WebMvcTest(AuthorizationController.class)
class AuthorizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorizationApiService service;

    @Test
    @DisplayName("Valid request returns the service's decision")
    void validRequestReturnsDecision() throws Exception {
        given(service.authorize(any()))
                .willReturn(new AuthorizeResponse("auth-1", "APPROVED"));

        String body = """
                {"merchantId":"m-1","amountCents":100,"currency":"USD"}
                """;

        mockMvc.perform(post("/authorizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorizationId").value("auth-1"))
                .andExpect(jsonPath("$.decision").value("APPROVED"));
    }

    @Test
    @DisplayName("Invalid request returns 400 with the stable error contract")
    void invalidRequestReturnsValidationError() throws Exception {
        // Missing currency and non-positive amount should be rejected before the
        // service is ever called.
        String body = """
                {"merchantId":"m-1","amountCents":0,"currency":""}
                """;

        mockMvc.perform(post("/authorizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.messages").isArray());
    }
}
