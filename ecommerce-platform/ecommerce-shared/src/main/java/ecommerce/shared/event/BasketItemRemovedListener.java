package ecommerce.shared.event;

import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;

public interface BasketItemRemovedListener extends EventListener {
    void onBasketItemRemoved(EventSource eventSource);
}
