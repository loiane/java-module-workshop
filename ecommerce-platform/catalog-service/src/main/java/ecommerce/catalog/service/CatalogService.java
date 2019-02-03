package ecommerce.catalog.service;

import ecommerce.catalog.core.Catalog;
import ecommerce.shared.model.Product;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class CatalogService {

    private static final AtomicInteger idGenerator = new AtomicInteger(0);

    private final Catalog catalog = new Catalog();

    public void delete(Integer productId) {

        catalog.delete(productId);

        System.out.println(String.format("CATALOG DELETED : {\"productId\":%d}",
                                         productId));
    }

    public Optional<Product> getProduct(Integer productId) {

        return catalog.get(productId);
    }

    public Product add(String productName, double unitPrice) {

        Product product = new Product(idGenerator.getAndAdd(1), productName, unitPrice);
        catalog.add(product);

        System.out.println(String.format("CATALOG ADDED : {\"product\":%s}",
                                         product.toString()));
        return product;
    }
}
