package com.nick.javafundamentals.exercises.aem02;

import java.util.List;
import org.apache.sling.api.resource.Resource;

/**
 * Reads help-center content through Sling's {@link Resource} abstraction
 * (exercise {@code AEM-02}).
 *
 * <p>Sling's core idea: <em>everything is a resource</em>. A resource wraps a
 * JCR node (or anything else) and exposes its properties as a
 * {@link org.apache.sling.api.resource.ValueMap}. Its
 * {@code sling:resourceType} property says which component should render it —
 * that string is how AEM decides what a piece of content *is*.
 *
 * <p>STUB: both methods are yours. The test {@code ArticleResourceReaderTest}
 * is the specification — remove its {@code @Disabled} and make it pass.
 */
public class ArticleResourceReader {

    /** The resource type that marks a resource as a help-center article. */
    public static final String ARTICLE_RESOURCE_TYPE = "visa/help/article";

    /**
     * The {@code title} property of the resource, or {@code "Untitled"} when the
     * property is absent. (Hint: {@link Resource#getValueMap()} — its two-arg
     * {@code get(name, defaultValue)} handles both the lookup and the fallback.)
     */
    public String titleOrDefault(Resource resource) {
        throw new UnsupportedOperationException(
                "TODO AEM-02: read the title from the ValueMap with a default");
    }

    /**
     * Titles of all children of {@code parent} whose resource type is
     * {@link #ARTICLE_RESOURCE_TYPE}, in child order. Non-article children
     * (folders, images, other components) are skipped.
     * (Hint: {@link Resource#getChildren()} and {@link Resource#isResourceType}.)
     */
    public List<String> articleTitles(Resource parent) {
        throw new UnsupportedOperationException(
                "TODO AEM-02: collect titleOrDefault of children that are articles");
    }
}
