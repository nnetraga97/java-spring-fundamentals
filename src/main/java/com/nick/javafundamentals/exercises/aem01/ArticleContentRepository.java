package com.nick.javafundamentals.exercises.aem01;

import java.util.Optional;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * Stores merchant help-center articles in the JCR — AEM's content store
 * (exercise {@code AEM-01}).
 *
 * <p>The mental shift from a database: JCR content is a <em>tree</em> of nodes
 * addressed by path (like {@code /content/help/cards/declined-payments}), and
 * each node carries free-form properties. There is no schema migration — you
 * create the structure by creating it.
 *
 * <p>STUB: both methods are yours. The test {@code ArticleContentRepositoryTest}
 * is the specification — remove its {@code @Disabled} and make it pass.
 */
public class ArticleContentRepository {

    /**
     * Creates (or updates) an article node at {@code path} with {@code title}
     * and {@code body} string properties, then saves the session.
     *
     * <p>Intermediate nodes must be created as needed: saving
     * {@code /content/help/cards/declined} when only {@code /content} exists
     * creates {@code help} and {@code cards} on the way. (Hint: walk segments
     * from the root with {@link javax.jcr.Node#hasNode} /
     * {@link javax.jcr.Node#addNode}, or look at {@code JcrUtils#getOrCreateByPath}.)
     *
     * <p>Saving twice to the same path must update the properties, not fail.
     */
    public void saveArticle(Session session, String path, String title, String body)
            throws RepositoryException {
        throw new UnsupportedOperationException(
                "TODO AEM-01: create intermediate nodes, set title/body properties, session.save()");
    }

    /**
     * Reads the {@code title} property of the node at {@code path}, or
     * {@link Optional#empty()} if the node or the property does not exist.
     */
    public Optional<String> findTitle(Session session, String path) throws RepositoryException {
        throw new UnsupportedOperationException(
                "TODO AEM-01: return the title property if the node and property exist");
    }
}
