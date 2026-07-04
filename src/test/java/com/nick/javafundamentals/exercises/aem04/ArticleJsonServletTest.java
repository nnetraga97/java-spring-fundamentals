package com.nick.javafundamentals.exercises.aem04;

import static org.assertj.core.api.Assertions.assertThat;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Spec for {@code AEM-04}. Remove {@code @Disabled}, run it (RED), then implement
 * {@link ArticleJsonServlet#doGet} until it is GREEN.
 *
 * <p>The mock request/response pair plays the role Sling plays in production:
 * {@code context.currentResource(...)} attaches the already-resolved resource to
 * the request, exactly what a real dispatch to {@code /content/help/fees.json}
 * would have done.
 */
@Disabled("AEM-04 — remove this line to begin the exercise")
@ExtendWith(AemContextExtension.class)
class ArticleJsonServletTest {

    private final AemContext context = new AemContext();

    private final ArticleJsonServlet servlet = new ArticleJsonServlet();

    @Test
    @DisplayName("a complete article is returned as JSON with the right content type")
    void writesArticleJson() throws Exception {
        context.create().resource("/content/help/fees",
                "sling:resourceType", "visa/help/article",
                "title", "Understanding fees",
                "body", "Fees depend on the card network.");
        context.currentResource("/content/help/fees");

        servlet.doGet(context.request(), context.response());

        assertThat(context.response().getStatus()).isEqualTo(HttpServletResponse.SC_OK);
        assertThat(context.response().getContentType()).startsWith("application/json");
        assertThat(context.response().getOutputAsString())
                .isEqualTo("{\"title\":\"Understanding fees\",\"body\":\"Fees depend on the card network.\"}");
    }

    @Test
    @DisplayName("a missing body is serialized as an empty string")
    void missingBodyBecomesEmptyString() throws Exception {
        context.create().resource("/content/help/stub-article",
                "sling:resourceType", "visa/help/article",
                "title", "Coming soon");
        context.currentResource("/content/help/stub-article");

        servlet.doGet(context.request(), context.response());

        assertThat(context.response().getOutputAsString())
                .isEqualTo("{\"title\":\"Coming soon\",\"body\":\"\"}");
    }

    @Test
    @DisplayName("an article without a title responds 404")
    void untitledArticleIs404() throws Exception {
        context.create().resource("/content/help/draft",
                "sling:resourceType", "visa/help/article",
                "body", "Not ready yet.");
        context.currentResource("/content/help/draft");

        servlet.doGet(context.request(), context.response());

        assertThat(context.response().getStatus()).isEqualTo(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    @DisplayName("quotes in content cannot break the JSON payload")
    void escapesQuotes() throws Exception {
        context.create().resource("/content/help/quotes",
                "sling:resourceType", "visa/help/article",
                "title", "The \"pending\" state",
                "body", "It means \"not settled yet\".");
        context.currentResource("/content/help/quotes");

        servlet.doGet(context.request(), context.response());

        assertThat(context.response().getOutputAsString())
                .isEqualTo("{\"title\":\"The \\\"pending\\\" state\",\"body\":\"It means \\\"not settled yet\\\".\"}");
    }
}
