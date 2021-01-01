package ecommerce.catalog.service;

import ecommerce.catalog.core.Catalog;
import ecommerce.shared.model.Product;

import jeventbus.service.EventBuilder;
import jeventbus.service.EventService;
import jeventbus.shared.Parameter;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


import static ecommerce.shared.event.ECommerceEventType.PRODUCT_ADDED;
import static ecommerce.shared.event.ECommerceEventType.PRODUCT_DELETED;

@ApplicationScoped
public class CatalogService {

    private static final AtomicLong idGenerator = new AtomicLong(0L);

    private final Catalog catalog = new Catalog();

    private final EventProducerService producerService;

    public CatalogService(EventProducerService producerService) {

        this.producerService = producerService;
    }

    public void delete(Long productId) {

        catalog.delete(productId);

        producerService.fireProductDeleted(productId);

    }

    public Optional<Product> getProduct(Long productId) {

        return catalog.get(productId);
    }

    public Product add(String productName, double unitPrice) {

        Product product = new Product(idGenerator.getAndAdd(1), productName, unitPrice);
        catalog.add(product);

        producerService.fireProductAdded(product.getId().longValue(), productName);

        return product;
    }
}
