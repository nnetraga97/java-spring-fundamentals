package com.nick.javafundamentals.exercises.aem05;

import static org.assertj.core.api.Assertions.assertThat;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Spec for {@code AEM-05}. Remove {@code @Disabled}, run it (RED), then implement
 * {@link MaintenanceBannerService} until it is GREEN.
 *
 * <p>{@code registerInjectActivateService} does what the OSGi container does in
 * production: instantiate the component, bind its configuration, inject its
 * references, and call {@code @Activate}.
 */
@Disabled("AEM-05 — remove this line to begin the exercise")
@ExtendWith(AemContextExtension.class)
class MaintenanceBannerServiceTest {

    private final AemContext context = new AemContext();

    @Test
    @DisplayName("configuration values are bound through @Activate")
    void bindsConfiguration() {
        MaintenanceBannerService service = context.registerInjectActivateService(
                new MaintenanceBannerService(),
                "enabled", true,
                "message", "Settlement reports delayed until 06:00 UTC");

        assertThat(service.isEnabled()).isTrue();
        assertThat(service.banner()).contains("Settlement reports delayed until 06:00 UTC");
    }

    @Test
    @DisplayName("with no configuration, the defaults apply: disabled, no banner")
    void defaultsApply() {
        MaintenanceBannerService service =
                context.registerInjectActivateService(new MaintenanceBannerService());

        assertThat(service.isEnabled()).isFalse();
        assertThat(service.banner()).isEmpty();
    }

    @Test
    @DisplayName("enabled but with a blank message still shows no banner")
    void blankMessageMeansNoBanner() {
        MaintenanceBannerService service = context.registerInjectActivateService(
                new MaintenanceBannerService(),
                "enabled", true,
                "message", "   ");

        assertThat(service.banner()).isEmpty();
    }

    @Test
    @DisplayName("another component receives this service through @Reference")
    void referenceInjectionWorks() {
        context.registerInjectActivateService(new MaintenanceBannerService(),
                "enabled", true,
                "message", "Maintenance tonight");

        HelpCenterHeaderProvider provider =
                context.registerInjectActivateService(new HelpCenterHeaderProvider());

        assertThat(provider.header()).isEqualTo("Help Center — Maintenance tonight");
    }
}
