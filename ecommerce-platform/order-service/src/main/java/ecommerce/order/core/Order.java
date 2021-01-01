package ecommerce.order.core;

import java.util.List;

public class Order {

    private Long buyerId;

    private Long basketId;

    private List<OrderItem> orderItems;

    public void setItems(List<OrderItem> orderItems) {

        this.orderItems = orderItems;
    }

    public void setBuyerId(Long buyerId) {

        this.buyerId = buyerId;
    }

    public void setBasketId(Long basketId) {
        this.basketId = basketId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public Long getBasketId() {
        return basketId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
