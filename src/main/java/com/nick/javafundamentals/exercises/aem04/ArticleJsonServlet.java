package com.nick.javafundamentals.exercises.aem04;

import java.io.IOException;
import javax.servlet.Servlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

/**
 * Serves a help-center article as JSON (exercise {@code AEM-04}).
 *
 * <p>The key difference from Spring MVC: a Sling servlet is registered against a
 * <em>resource type</em>, not a URL pattern. Requesting
 * {@code /content/help/fees.json} resolves the resource at
 * {@code /content/help/fees}, sees its {@code sling:resourceType}, and — because
 * that type matches this servlet's registration below — dispatches here with the
 * resource already attached to the request. Content drives routing.
 *
 * <p>The OSGi registration is provided (read it — it is half the interview
 * answer). STUB: {@link #doGet} is yours. The test {@code ArticleJsonServletTest}
 * is the specification — remove its {@code @Disabled} and make it pass.
 */
@Component(service = Servlet.class,
        property = {
                "sling.servlet.resourceTypes=visa/help/article",
                "sling.servlet.extensions=json",
                "sling.servlet.methods=GET"
        })
public class ArticleJsonServlet extends SlingSafeMethodsServlet {

    /**
     * Writes the current resource's article data as JSON.
     *
     * <p>Contract (see the test for exact shapes):
     * <ul>
     *   <li>Content type {@code application/json}, charset UTF-8.</li>
     *   <li>Body {@code {"title":"...","body":"..."}} from the resource's
     *       properties ({@code request.getResource()} — Sling already resolved
     *       it for you).</li>
     *   <li>If the resource has no {@code title} property, respond
     *       {@code 404} — an article without a title is not publishable.</li>
     * </ul>
     */
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {
        throw new UnsupportedOperationException(
                "TODO AEM-04: read the resource's ValueMap, 404 without a title, else write JSON");
    }

    /** Minimal JSON string escaping so quotes and backslashes in content cannot break the payload. */
    static String jsonEscape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
