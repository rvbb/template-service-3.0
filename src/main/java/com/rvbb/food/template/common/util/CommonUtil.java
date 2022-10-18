package com.rvbb.food.template.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtil {

    private static SecureRandom numberGenerator = null;
    private static final long MSB = 0x3200000000000000L;

    public static String unique() {
        SecureRandom secureRandom = numberGenerator;
        if (secureRandom == null) {
            numberGenerator = secureRandom = new SecureRandom();
        }
        return Long.toHexString(MSB | secureRandom.nextLong()) + Long.toHexString(MSB | secureRandom.nextLong());
    }
}
