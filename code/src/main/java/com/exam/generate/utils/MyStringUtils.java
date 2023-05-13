package com.exam.generate.utils;

/**
 * @author: ZhangX
 * @createDate: 2023/5/13
 * @description: 字符串工具类
 */
public class MyStringUtils {
    public static String upCaseString(String word){
        if(word.length() ==0 ){
            return word;
        }
        return word.substring(0,1).toUpperCase() + word.substring(1);
    }

    public static String lowCaseString(String word){
        if(word.length() ==0 ){
            return word;
        }
        return word.substring(0,1).toLowerCase() + word.substring(1);
    }
}

