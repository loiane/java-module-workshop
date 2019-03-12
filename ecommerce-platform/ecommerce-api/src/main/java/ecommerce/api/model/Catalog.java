package ecommerce.api.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Catalog {

    private Map<Integer, Product> products = new HashMap<>();

    public void add(Product product) {
        products.put(product.getId(), product);
    }

    public Optional<Product> get(Integer id) {
        return Optional.ofNullable(products.get(id));
    }

    public void delete(Integer id) {
        products.remove(id);
    }
}
