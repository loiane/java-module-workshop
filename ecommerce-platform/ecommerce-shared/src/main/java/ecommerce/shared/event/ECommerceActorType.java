package ecommerce.shared.event;

import jeventbus.streaming.ActorType;

public enum ECommerceActorType implements ActorType<ECommerceActorType> {
    BUYER,
    SELLER,
    SYSTEM;

    @Override
    public ECommerceActorType fromName(String name) {
        return ECommerceActorType.valueOf(name);
    }
}