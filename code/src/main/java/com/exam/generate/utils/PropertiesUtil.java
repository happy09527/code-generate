package com.exam.generate.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: ZhangX
 * @createDate: 2023/5/13
 * @description:
 */
public class PropertiesUtil {
    private static Properties properties = new Properties();
    private static Map<String, String> propMap = new ConcurrentHashMap<>();

    static {
        InputStream inputStream = null;
        try {
            inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);
            Iterator<Object> iterator = properties.keySet().iterator();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                propMap.put(key,properties.getProperty(key));
            }
        } catch (Exception e) {

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public static String getKey(String key){
        return propMap.get(key);
    }
}
