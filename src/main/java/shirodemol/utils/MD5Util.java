package shirodemol.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class MD5Util {
    public static String getMD5Password(String password) {
        String algorithmName = "MD5"; //加密算法
        int hashIterations = 10000;//重复加密次数
        ByteSource salt = ByteSource.Util.bytes("CGC_crm");//盐值
        SimpleHash simpleHash = new SimpleHash(algorithmName, password, salt, hashIterations);
        return simpleHash.toString();//加密以后得到密码
    }
}
