package ecommerce.shared.event;

import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;

public interface BasketClearedListener extends EventListener {
    void onBasketCleared(EventSource eventSource);
}
