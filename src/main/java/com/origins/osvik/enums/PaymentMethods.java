package com.origins.osvik.enums;

/**
 * Created by Amila-Kumara on 6/19/2016.
 */
public enum PaymentMethods {

    CASH_INVOICE("Cash Invoice", 1), CREDIT_INVOICE("Credit Invoice", 2), SAMPLE_INVOICE("Sample Invoice", 3);

    private String name;
    private Integer code;

    PaymentMethods(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
