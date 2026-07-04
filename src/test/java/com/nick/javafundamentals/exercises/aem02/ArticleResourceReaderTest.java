package com.nick.javafundamentals.exercises.aem02;

import static org.assertj.core.api.Assertions.assertThat;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Spec for {@code AEM-02}. Remove {@code @Disabled}, run it (RED), then implement
 * {@link ArticleResourceReader} until it is GREEN.
 */
@Disabled("AEM-02 — remove this line to begin the exercise")
@ExtendWith(AemContextExtension.class)
class ArticleResourceReaderTest {

    private final AemContext context = new AemContext();

    private final ArticleResourceReader reader = new ArticleResourceReader();

    @Test
    @DisplayName("titleOrDefault reads the title property")
    void readsTitle() {
        Resource article = context.create().resource("/content/help/fees",
                "sling:resourceType", ArticleResourceReader.ARTICLE_RESOURCE_TYPE,
                "title", "Understanding fees");

        assertThat(reader.titleOrDefault(article)).isEqualTo("Understanding fees");
    }

    @Test
    @DisplayName("titleOrDefault falls back to 'Untitled' when the property is missing")
    void fallsBackToDefault() {
        Resource article = context.create().resource("/content/help/draft",
                "sling:resourceType", ArticleResourceReader.ARTICLE_RESOURCE_TYPE);

        assertThat(reader.titleOrDefault(article)).isEqualTo("Untitled");
    }

    @Test
    @DisplayName("articleTitles lists only children typed as articles, in order")
    void listsOnlyArticleChildren() {
        Resource parent = context.create().resource("/content/help");
        context.create().resource("/content/help/a",
                "sling:resourceType", ArticleResourceReader.ARTICLE_RESOURCE_TYPE,
                "title", "First article");
        context.create().resource("/content/help/assets",
                "sling:resourceType", "sling:Folder",
                "title", "Not an article");
        context.create().resource("/content/help/b",
                "sling:resourceType", ArticleResourceReader.ARTICLE_RESOURCE_TYPE,
                "title", "Second article");

        assertThat(reader.articleTitles(parent))
                .containsExactly("First article", "Second article");
    }

    @Test
    @DisplayName("an article child without a title shows up as 'Untitled'")
    void untitledChildUsesDefault() {
        Resource parent = context.create().resource("/content/help");
        context.create().resource("/content/help/mystery",
                "sling:resourceType", ArticleResourceReader.ARTICLE_RESOURCE_TYPE);

        assertThat(reader.articleTitles(parent)).containsExactly("Untitled");
    }

    @Test
    @DisplayName("a parent with no children yields an empty list")
    void emptyParent() {
        Resource parent = context.create().resource("/content/empty");

        assertThat(reader.articleTitles(parent)).isEmpty();
    }
}
