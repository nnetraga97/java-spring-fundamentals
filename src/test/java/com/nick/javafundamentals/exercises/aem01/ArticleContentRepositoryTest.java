package com.nick.javafundamentals.exercises.aem01;

import static org.assertj.core.api.Assertions.assertThat;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import javax.jcr.Session;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Spec for {@code AEM-01}. Remove {@code @Disabled}, run it (RED), then implement
 * {@link ArticleContentRepository} until it is GREEN.
 *
 * <p>{@link AemContext} gives every test an in-memory Sling/JCR environment —
 * no real AEM instance involved. Adapting the resource resolver to
 * {@link Session} drops you down to the raw JCR API this exercise practices.
 */
@Disabled("AEM-01 — remove this line to begin the exercise")
@ExtendWith(AemContextExtension.class)
class ArticleContentRepositoryTest {

    private final AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

    private final ArticleContentRepository repository = new ArticleContentRepository();

    private Session session() {
        return context.resourceResolver().adaptTo(Session.class);
    }

    @Test
    @DisplayName("saving an article creates the node with title and body properties")
    void savesArticleNode() throws Exception {
        repository.saveArticle(session(), "/content/help/declined-payments",
                "Why was my payment declined?", "Common decline reasons are...");

        Session session = session();
        assertThat(session.nodeExists("/content/help/declined-payments")).isTrue();
        assertThat(session.getNode("/content/help/declined-payments")
                .getProperty("title").getString())
                .isEqualTo("Why was my payment declined?");
        assertThat(session.getNode("/content/help/declined-payments")
                .getProperty("body").getString())
                .isEqualTo("Common decline reasons are...");
    }

    @Test
    @DisplayName("intermediate nodes are created on the way to a deep path")
    void createsIntermediateNodes() throws Exception {
        repository.saveArticle(session(), "/content/help/cards/chargebacks/how-to-dispute",
                "How to dispute a charge", "...");

        Session session = session();
        assertThat(session.nodeExists("/content/help/cards")).isTrue();
        assertThat(session.nodeExists("/content/help/cards/chargebacks")).isTrue();
    }

    @Test
    @DisplayName("saving to the same path twice updates instead of failing")
    void savingTwiceUpdates() throws Exception {
        repository.saveArticle(session(), "/content/help/fees", "Fees v1", "old");
        repository.saveArticle(session(), "/content/help/fees", "Fees v2", "new");

        assertThat(repository.findTitle(session(), "/content/help/fees"))
                .contains("Fees v2");
    }

    @Test
    @DisplayName("findTitle returns the stored title")
    void findsTitle() throws Exception {
        repository.saveArticle(session(), "/content/help/refunds", "Refund timelines", "...");

        assertThat(repository.findTitle(session(), "/content/help/refunds"))
                .contains("Refund timelines");
    }

    @Test
    @DisplayName("findTitle is empty for a missing node and for a node without a title")
    void findTitleHandlesMissingData() throws Exception {
        assertThat(repository.findTitle(session(), "/content/nowhere")).isEmpty();

        context.create().resource("/content/help/untitled", "body", "no title here");
        assertThat(repository.findTitle(session(), "/content/help/untitled")).isEmpty();
    }
}
