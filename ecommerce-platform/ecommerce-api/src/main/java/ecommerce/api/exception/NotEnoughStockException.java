package ecommerce.api.exception;

import static java.lang.String.format;

public class NotEnoughStockException extends Exception {

    private final Integer productId;

    private final Integer countToCheckout;

    private final Integer countInStock;

    public NotEnoughStockException(Integer productId, Integer countToCheckout, Integer countInStock) {

        this.productId = productId;
        this.countToCheckout = countToCheckout;
        this.countInStock = countInStock;
    }

    @Override
    public String getMessage() {
        return format("Not enough stock for product %d. Count To Checkout : %d / Count In Stock : %d", productId, countToCheckout, countInStock);
    }
}
