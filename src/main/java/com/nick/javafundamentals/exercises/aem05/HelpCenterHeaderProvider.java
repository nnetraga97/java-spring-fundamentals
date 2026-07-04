package com.nick.javafundamentals.exercises.aem05;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Consumes {@link MaintenanceBannerService} through an OSGi {@link Reference}
 * (exercise {@code AEM-05} — provided complete; this is the consuming side you
 * read, not write).
 *
 * <p>{@code @Reference} is OSGi's {@code @Autowired}: the container finds a
 * registered service of the field's type and injects it before activation.
 */
@Component(service = HelpCenterHeaderProvider.class)
public class HelpCenterHeaderProvider {

    @Reference
    private MaintenanceBannerService bannerService;

    /** The header line for help-center pages, including the banner when active. */
    public String header() {
        return bannerService.banner()
                .map(banner -> "Help Center — " + banner)
                .orElse("Help Center");
    }
}
