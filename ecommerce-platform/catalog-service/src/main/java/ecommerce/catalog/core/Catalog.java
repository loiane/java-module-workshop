package ecommerce.catalog.core;

import ecommerce.shared.model.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Catalog {

    private Map<Long, Product> products = new HashMap<>();

    public void add(Product product) {
        products.put(product.getId(), product);
    }

    public Optional<Product> get(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    public void delete(Long id) {
        products.remove(id);
    }
}
