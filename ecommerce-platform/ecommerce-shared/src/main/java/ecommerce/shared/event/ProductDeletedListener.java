package ecommerce.shared.event;

import jeventbus.shared.EventSource;
import jeventbus.shared.EventListener;

public interface ProductDeletedListener extends EventListener {
    void onProductDeleted(EventSource source);
}