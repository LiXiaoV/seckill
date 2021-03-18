package com.xiaov.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;


/**
 * @author xiaov
 * @since 2021-03-03 14:28
 */
public class MD5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "201921043844scutliwei";

    public static String inputPassToFormPass(String inputPass){
        String str = "" + salt.charAt(2)+salt.charAt(0)+ inputPass +salt.charAt(1)+salt.charAt(9);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass,String saltDB){
        String str = "" + saltDB.charAt(2)+saltDB.charAt(0)+ formPass +saltDB.charAt(1)+saltDB.charAt(9);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass,String saltDB){
        String formPass = inputPassToFormPass(inputPass);
        return formPassToDBPass(formPass,saltDB);
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123456"));
        System.out.println(formPassToDBPass(inputPassToFormPass("123456"),"1234567890"));
        System.out.println(inputPassToDBPass("liwei666","201921043844"));
    }

}
