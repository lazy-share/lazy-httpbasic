package com.lazy.httpbasic.util;

import java.security.MessageDigest;
import java.security.SignatureException;

/**
 * @author laizhiyuan
 * @date 2017/12/26.
 */
public abstract class MD5Util {

    /**
     * 公盐
     */
    private static final String PUBLIC_SALT = "";
    /**
     * 十六进制下数字到字符的映射数组
     */
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};


    /**
     * md5 加密算法
     *
     * @param clientString 字符串
     * @return
     */
    public static String encodeByMD5(String clientString) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(clientString.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (Exception e) {

            return null;
        }
    }


    public static void main(String[] args) throws SignatureException {
        System.out.println(encodeByMD5("weprinefcfwqkl;r3245353rq"));
    }

}
