package com.xt.cloud.userservice;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * Created by shawn on 2017/11/20.
 */
public class EncryptUtil {
    private static final String SECRET = "fruit-cloud";
    private static final PasswordEncoder encoder = new StandardPasswordEncoder(SECRET);

    public static String encrypt(String clearPassword) {
        return encoder.encode(clearPassword);
    }

    public static boolean match(String clearPassword, String password) {
        return encoder.matches(clearPassword, password);
    }
}
