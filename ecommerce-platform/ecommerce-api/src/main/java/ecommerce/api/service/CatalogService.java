package ecommerce.api.service;

import ecommerce.api.model.Catalog;
import ecommerce.api.model.Product;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;

public class CatalogService {

    private static final AtomicInteger idGenerator = new AtomicInteger(0);

    private final Catalog catalog = new Catalog();

    public void delete(Integer productId) {

        catalog.delete(productId);

        System.out.println(format("CATALOG DELETED : {\"productId\":%d}",
                                  productId));
    }

    public Optional<Product> getProduct(Integer productId) {

        return catalog.get(productId);
    }

    public Product add(String productName, double unitPrice) {

        Product product = new Product(idGenerator.getAndAdd(1), productName, unitPrice);

        catalog.add(product);

        System.out.println(format("CATALOG ADDED : {\"product\":%s}",
                                         product.toString()));
        return product;
    }
}
