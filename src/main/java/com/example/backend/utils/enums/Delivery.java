package com.example.backend.utils.enums;

import lombok.Getter;

import static com.example.backend.utils.general.Constants.DELIVERY_MESSAGE;

@Getter
public enum Delivery {
    NOVA_POSHTA(DELIVERY_MESSAGE + "Нову Пошту"),
    MEEST_EXPRESS(DELIVERY_MESSAGE + "Meest Express"),
    JUSTIN(DELIVERY_MESSAGE + "Justin"),
    UKRPOSHTA(DELIVERY_MESSAGE + "УкрПошту"),
    PICKUP(DELIVERY_MESSAGE + "Самовивізом");

    private final String name;

    Delivery(final String name) {
        this.name = name;
    }
}