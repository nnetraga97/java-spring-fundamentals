package com.nick.javafundamentals.exercises.aem05;

import java.util.Optional;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * An OSGi service that decides whether the help center shows a maintenance
 * banner (exercise {@code AEM-05}).
 *
 * <p>OSGi is AEM's module and dependency-injection system — the role Spring
 * plays elsewhere. A {@link Component} is instantiated by the container, its
 * {@link Activate} method receives typed configuration (editable at runtime in
 * the Felix console, no redeploy), and other components obtain it via
 * {@code @Reference}. See {@link HelpCenterHeaderProvider} for the consuming
 * side — it is provided complete.
 *
 * <p>STUB: {@link #activate}, {@link #isEnabled} and {@link #banner} are yours.
 * The test {@code MaintenanceBannerServiceTest} is the specification — remove
 * its {@code @Disabled} and make it pass.
 */
@Component(service = MaintenanceBannerService.class)
@Designate(ocd = MaintenanceBannerService.Config.class)
public class MaintenanceBannerService {

    /** Typed configuration; defaults apply when no configuration is supplied. */
    @ObjectClassDefinition(name = "Help Center Maintenance Banner")
    public @interface Config {

        @AttributeDefinition(description = "Show the maintenance banner")
        boolean enabled() default false;

        @AttributeDefinition(description = "Banner text shown to merchants")
        String message() default "";
    }

    /**
     * Called by the container on startup and again whenever the configuration
     * changes. Copy the config values into fields here.
     */
    @Activate
    protected void activate(Config config) {
        throw new UnsupportedOperationException(
                "TODO AEM-05: store config.enabled() and config.message() in fields");
    }

    public boolean isEnabled() {
        throw new UnsupportedOperationException("TODO AEM-05: the configured enabled flag");
    }

    /**
     * The banner text when enabled *and* a non-blank message is configured;
     * otherwise empty. (An enabled banner with no text would render as an empty
     * yellow bar — treat it as disabled.)
     */
    public Optional<String> banner() {
        throw new UnsupportedOperationException(
                "TODO AEM-05: message when enabled and non-blank, otherwise Optional.empty()");
    }
}
