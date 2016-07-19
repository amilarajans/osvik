package com.origins.osvik.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amila-Kumara on 20/07/2016.
 */
public enum CauseOfReturn {
    DAMAGED("Damaged", 1), EXPIRED("Expired", 2), WRONG_ITEM("Wrong Item", 3), OTHER("Other", 4);

    static Map<Integer, CauseOfReturn> map = new HashMap<>();

    static {
        for (CauseOfReturn causeOfReturn : CauseOfReturn.values()) {
            map.put(causeOfReturn.code, causeOfReturn);
        }
    }

    private String name;
    private Integer code;

    CauseOfReturn(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public static CauseOfReturn getByCode(int code) {
        return map.get(code);
    }

    public static CauseOfReturn getByPosition(int positionCode) {
        return CauseOfReturn.values()[positionCode - 1];
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
