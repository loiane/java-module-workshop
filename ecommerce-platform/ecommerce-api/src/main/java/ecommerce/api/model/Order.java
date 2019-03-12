package ecommerce.api.model;

import java.util.List;

public class Order {

    private Integer buyerId;

    private Integer basketId;

    private List<OrderItem> orderItems;

    public void setItems(List<OrderItem> orderItems) {

        this.orderItems = orderItems;
    }

    public void setBuyerId(Integer buyerId) {

        this.buyerId = buyerId;
    }

    public void setBasketId(Integer basketId) {
        this.basketId = basketId;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public Integer getBasketId() {
        return basketId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
