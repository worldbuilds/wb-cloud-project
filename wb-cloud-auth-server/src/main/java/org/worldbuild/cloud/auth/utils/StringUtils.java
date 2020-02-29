package org.worldbuild.cloud.auth.utils;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

public interface StringUtils {

    public static String generateSecret() {
         return RandomStringUtils.random(10, true, true).toUpperCase();
    }

    public static String generateSecret(int count) {
        return RandomStringUtils.random(count, true, true).toUpperCase();
    }

    public static String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[10];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

}
