package com.exam.generate.bean;

import com.exam.generate.utils.PropertiesUtil;

/**
 * @author: ZhangX
 * @createDate: 2023/5/13
 * @description: 部分常量，properties
 */
public class Constants {
    public static String IGNORE_TABLE_PREFIX;

    static {
        IGNORE_TABLE_PREFIX = (String) PropertiesUtil.getKey("spring.batch.table-prefix");
    }

    public final static String[] SQL_DATE_TIME_TYPES = new String[]{"datetime", "timestamp"};
    public final static String[] SQL_DATE_TYPES = new String[]{"date"};
    public static final String[] SQL_DECIMAL_TYPE = new String[]{"decimal", "double", "float"};
    public static final String[] SQL_STRING_TYPE = new String[]{"char", "varchar", "text", "mediumtext", "longtext"};
    // Integer
    public static final String[] SQL_INTEGER_TYPE = new String[]{"int", "tinyint"};
    //Long
    public static final String[] SQL_LONG_TYPE = new String[]{"bigint"};

}
