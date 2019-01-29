package ecommerce.order.core;

import ecommerce.shared.model.ItemWithCount;

public class OrderItem {

    private final ItemWithCount item;

    public OrderItem(ItemWithCount item) {
        this.item = item;
    }

    public ItemWithCount getItem() {
        return item;
    }
}
