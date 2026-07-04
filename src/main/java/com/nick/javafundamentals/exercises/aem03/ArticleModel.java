package com.nick.javafundamentals.exercises.aem03;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * A Sling Model for a help-center article (exercise {@code AEM-03}).
 *
 * <p>Sling Models are AEM's answer to "stop writing ValueMap lookups by hand":
 * annotate a class with {@link Model}, declare which properties you want with
 * {@link ValueMapValue}, and {@code resource.adaptTo(ArticleModel.class)} hands
 * you a populated object. This is the class most AEM component development
 * revolves around — the HTL template reads its getters.
 *
 * <p>The injection wiring is already provided (read it — interviewers ask what
 * {@code adaptables} and the injection strategy mean). STUB: the two derived
 * getters are yours. The test {@code ArticleModelTest} is the specification —
 * remove its {@code @Disabled} and make it pass.
 */
@Model(adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ArticleModel {

    static final int TEASER_MAX_LENGTH = 50;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String body;

    /**
     * The title, or {@code "Untitled"} when the content has no title property.
     * (With the OPTIONAL injection strategy, a missing property leaves the
     * field {@code null} instead of failing adaptation.)
     */
    public String displayTitle() {
        throw new UnsupportedOperationException("TODO AEM-03: title, or 'Untitled' when null");
    }

    /**
     * A teaser for search results: the body trimmed to at most
     * {@value #TEASER_MAX_LENGTH} characters, with {@code "..."} appended when it
     * was cut. A missing body yields an empty string.
     */
    public String teaser() {
        throw new UnsupportedOperationException(
                "TODO AEM-03: first " + TEASER_MAX_LENGTH + " chars of body, plus '...' if truncated");
    }
}
