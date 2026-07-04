package com.nick.javafundamentals.exercises.aem03;

import static org.assertj.core.api.Assertions.assertThat;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Spec for {@code AEM-03}. Remove {@code @Disabled}, run it (RED), then implement
 * {@link ArticleModel#displayTitle} and {@link ArticleModel#teaser} until GREEN.
 */
@Disabled("AEM-03 — remove this line to begin the exercise")
@ExtendWith(AemContextExtension.class)
class ArticleModelTest {

    private final AemContext context = new AemContext();

    @BeforeEach
    void registerModel() {
        context.addModelsForClasses(ArticleModel.class);
    }

    private ArticleModel adapt(Resource resource) {
        ArticleModel model = resource.adaptTo(ArticleModel.class);
        assertThat(model).as("adaptTo should produce a model — is @Model wiring intact?")
                .isNotNull();
        return model;
    }

    @Test
    @DisplayName("properties are injected from the resource and exposed by the getters")
    void injectsProperties() {
        Resource resource = context.create().resource("/content/help/fees",
                "title", "Understanding fees",
                "body", "Short body.");

        ArticleModel model = adapt(resource);

        assertThat(model.displayTitle()).isEqualTo("Understanding fees");
        assertThat(model.teaser()).isEqualTo("Short body.");
    }

    @Test
    @DisplayName("a long body is trimmed to 50 chars with an ellipsis")
    void trimsLongBody() {
        String longBody = "x".repeat(80);
        Resource resource = context.create().resource("/content/help/long",
                "title", "Long", "body", longBody);

        assertThat(adapt(resource).teaser()).isEqualTo("x".repeat(50) + "...");
    }

    @Test
    @DisplayName("a body of exactly 50 chars is not given an ellipsis")
    void exactLengthIsNotTrimmed() {
        String body = "y".repeat(50);
        Resource resource = context.create().resource("/content/help/exact",
                "title", "Exact", "body", body);

        assertThat(adapt(resource).teaser()).isEqualTo(body);
    }

    @Test
    @DisplayName("missing properties fall back: 'Untitled' title, empty teaser")
    void missingPropertiesFallBack() {
        Resource resource = context.create().resource("/content/help/empty");

        ArticleModel model = adapt(resource);

        assertThat(model.displayTitle()).isEqualTo("Untitled");
        assertThat(model.teaser()).isEmpty();
    }
}
