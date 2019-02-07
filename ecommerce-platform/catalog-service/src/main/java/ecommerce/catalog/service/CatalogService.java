package ecommerce.catalog.service;

import ecommerce.catalog.core.Catalog;
import ecommerce.shared.model.Product;

import jeventbus.service.EventBuilder;
import jeventbus.service.EventService;
import jeventbus.shared.Parameter;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


import static ecommerce.shared.event.ECommerceEventType.PRODUCT_ADDED;
import static ecommerce.shared.event.ECommerceEventType.PRODUCT_DELETED;

public class CatalogService {

    private static final AtomicInteger idGenerator = new AtomicInteger(0);

    private final Catalog catalog = new Catalog();

    private final EventService eventService;

    public CatalogService(EventService eventService) {

        this.eventService = eventService;
    }

    public void delete(Integer productId) {

        catalog.delete(productId);

        eventService.fire(PRODUCT_DELETED, Parameter.by("productId", productId));

        System.out.println(String.format("CATALOG DELETED : {\"productId\":%d}",
                                         productId));
    }

    public Optional<Product> getProduct(Integer productId) {

        return catalog.get(productId);
    }

    public Product add(String productName, double unitPrice) {

        Product product = new Product(idGenerator.getAndAdd(1), productName, unitPrice);
        catalog.add(product);

        eventService.fire(PRODUCT_ADDED, Parameter.by("product", product.toString()));

        System.out.println(String.format("CATALOG ADDED : {\"product\":%s}",
                                         product.toString()));
        return product;
    }
}
