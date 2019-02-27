package com.hxzk_bj_demo.utils;

import java.security.MessageDigest;


/**
 * @Created HaiyuKing
 * @Used  MD5生成
 */
public class Md5Utils {
    
    /**
     * MD5加密，32位
     * @param str - 加密前的字符串
     * http://hubingforever.blog.163.com/blog/static/171040579201210781650340/
     * <br/>1、创建 MessageDigest 对象：通过getInstance("MD5")
     * <br/>2、向MessageDigest传送要计算的数据：生成加密后的字符串字节数组
     * <br/>3、生成加密后的字符串：根据加密后的字符串字节数组生成
     * */
    public static String MD5(String str)
    {
        MessageDigest md5 = null;
        try
        {
        	/*
        	 * 生成实现指定摘要算法的 MessageDigest 对象
        	 * 其中传入的参数指定计算消息摘要所使用的算法，常用的有"MD5"，"SHA"等。
        	 * */
            md5 = MessageDigest.getInstance("MD5");
            
        } catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
        
        //生成加密后字符串字节数组:方式一
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
        {
            byteArray[i] = (byte) charArray[i];
        }
        
        byte[] md5Bytes = md5.digest(byteArray);
        
        //生成加密后的字符串
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++)
        {
            int val = (md5Bytes[i]) & 0xff;
            if (val < 16)
            {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    
}