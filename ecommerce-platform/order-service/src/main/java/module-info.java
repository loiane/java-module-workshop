module order.service {
    exports ecommerce.order.service;
    exports ecommerce.order.core to ecommerce.api;
    requires ecommerce.shared;
    requires jeventbus.core;
    requires jeventbus.kafka;
    requires jeventbus.enterprise;
    requires java.ws.rs;
    requires jakarta.inject.api;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires quarkus.jackson;
    requires jakarta.enterprise.cdi.api;
    requires quarkus.core;
}