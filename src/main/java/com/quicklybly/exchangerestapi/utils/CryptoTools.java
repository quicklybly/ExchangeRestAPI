package com.quicklybly.exchangerestapi.utils;

import org.hashids.Hashids;

import java.util.Objects;

public class CryptoTools {
    private final Hashids hashids;

    public CryptoTools(String salt) {
        int minHashLength = 10;
        this.hashids = new Hashids(salt, minHashLength);
    }

    public Long idOf(String value) {
        long[] result = hashids.decode(value);
        if (Objects.nonNull(result) && result.length > 0) {
            return result[0];
        }
        return null;
    }

    public String hashOf(Long value) {
        return hashids.encode(value);
    }
}
