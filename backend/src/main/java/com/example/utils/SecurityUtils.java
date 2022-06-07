package com.example.utils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class SecurityUtils {
    /**
     * 密码编码
     * @param password
     * @return
     */
    public static String encodePassword(String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    /**
     * 密码匹配
     * @param rawPassword
     * @param encodePassword
     * @return
     */
    public static boolean matchesPassword(String rawPassword,String encodePassword){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(rawPassword,encodePassword);
    }
}
