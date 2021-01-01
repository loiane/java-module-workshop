package ecommerce.shared.event;

import jeventbus.streaming.EventChannel;

public enum ECommerceEventChannel implements EventChannel<ECommerceEventChannel> {
    DESKTOP_WEB,
    MOBILE_WEB,
    STANDALONE_APP;

    @Override
    public ECommerceEventChannel fromName(String name) {
        return valueOf(name);
    }
}