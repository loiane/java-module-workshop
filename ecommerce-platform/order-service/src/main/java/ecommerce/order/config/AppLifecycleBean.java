package ecommerce.order.config;

import ecommerce.order.service.OrderService;
import ecommerce.shared.event.ECommerceEventType;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jeventbus.service.EventBuilder;
import jeventbus.service.EventService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class AppLifecycleBean {

    @Inject
    OrderService orderService;

    void onStart(@Observes StartupEvent ev) {
        System.out.println("Order Service is starting...");
        System.out.println("Registering listeners to events...");
    }

    void onStop(@Observes ShutdownEvent ev) {               
        System.out.println("The application is stopping...");
    }

}