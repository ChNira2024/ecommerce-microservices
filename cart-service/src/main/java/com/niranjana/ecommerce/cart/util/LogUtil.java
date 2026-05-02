package com.niranjana.ecommerce.cart.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LogUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            return "Error converting to JSON";
        }
    }
}