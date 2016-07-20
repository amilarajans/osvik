package com.origins.osvik.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amila-Kumara on 6/19/2016.
 */
public enum PaymentMethods {

    CASH_INVOICE("Cash Invoice", 1), CREDIT_INVOICE("Credit Invoice", 2), SAMPLE_INVOICE("Sample Invoice", 3);

    static Map<Integer, PaymentMethods> map = new HashMap<>();

    static {
        for (PaymentMethods method : PaymentMethods.values()) {
            map.put(method.code, method);
        }
    }

    private String name;
    private Integer code;

    PaymentMethods(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public static PaymentMethods getByCode(int code) {
        return map.get(code);
    }

    public static PaymentMethods getByPosition(int positionCode) {
        return PaymentMethods.values()[positionCode - 1];
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
