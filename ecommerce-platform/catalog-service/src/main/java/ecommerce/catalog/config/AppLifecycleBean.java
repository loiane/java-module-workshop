package ecommerce.catalog.config;

import ecommerce.catalog.service.CatalogService;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jeventbus.service.EventService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class AppLifecycleBean {

    @Inject
    private CatalogService catalogService;

    void onStart(@Observes StartupEvent ev) {
        System.out.println("Catalog Service is starting...");
        System.out.println("Registering listeners to events...");

        EventService jeventbusService = new EventService();
    }

    void onStop(@Observes ShutdownEvent ev) {               
        System.out.println("The application is stopping...");
    }

}