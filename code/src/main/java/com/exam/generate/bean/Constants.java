package com.exam.generate.bean;

import com.exam.generate.utils.PropertiesUtil;

/**
 * @author: ZhangX
 * @createDate: 2023/5/13
 * @description: 部分常量，properties
 */
public class Constants {
    public static String IGNORE_TABLE_PREFIX;


    public static String IGNORE_BEAN_FIELD;
    public static String IGNORE_BEAN_EXPRESSION;
    public static String IGNORE_BEAN_CLASS;
    public static String BEAN_DATA_FORMAT_EXPRESSION;
    public static String BEAN_DATA_FORMAT_CLASS;
    public static String BEAN_DATA_UNFORMAT_EXPRESSION;
    public static String BEAN_DATA_UNFORMAT_CLASS;

    public static String PATH_BASE;
    public static String PACKAGE_BASE;
    public static String PATH_JAVA = "java";
    public static String PATH_RESOURCE = "resource";
    public static String PATH_PARAM;
    public static String PATH_PO;

    public static String PATH_UTILS;
    public static String PACKAGE_PO;
    public static String PACKAGE_PARAM;
    public static String PACKAGE_UTILS;
    public static String PACKAGE_ENUMS;
    public static String PATH_ENUMS;

    public static String AUTHOR;


    public final static String[] SQL_DATE_TIME_TYPES = new String[]{"datetime", "timestamp"};
    public final static String[] SQL_DATE_TYPES = new String[]{"date"};
    public static final String[] SQL_DECIMAL_TYPE = new String[]{"decimal", "double", "float"};
    public static final String[] SQL_STRING_TYPE = new String[]{"char", "varchar", "text", "mediumtext", "longtext"};
    // Integer
    public static final String[] SQL_INTEGER_TYPE = new String[]{"int", "tinyint"};
    //Long
    public static final String[] SQL_LONG_TYPE = new String[]{"bigint"};

    static {
        IGNORE_TABLE_PREFIX = PropertiesUtil.getKey("spring.batch.table-prefix");

        IGNORE_BEAN_FIELD = PropertiesUtil.getKey("ignore.bean.field");
        IGNORE_BEAN_EXPRESSION = PropertiesUtil.getKey("ignore.bean.expression");
        IGNORE_BEAN_CLASS = PropertiesUtil.getKey("ignore.bean.class");
        BEAN_DATA_FORMAT_EXPRESSION = PropertiesUtil.getKey("bean.data.format.expression");
        BEAN_DATA_FORMAT_CLASS = PropertiesUtil.getKey("bean.data.format.class");
        BEAN_DATA_UNFORMAT_EXPRESSION = PropertiesUtil.getKey("bean.data.unformat.expression");
        BEAN_DATA_UNFORMAT_CLASS = PropertiesUtil.getKey("bean.data.unformat.class");

        PATH_BASE = PropertiesUtil.getKey("path.base");
        PACKAGE_BASE = PropertiesUtil.getKey("package.base");
        PACKAGE_PO = PACKAGE_BASE + "." + PropertiesUtil.getKey("package.po");
        PACKAGE_PARAM = PACKAGE_BASE + "." + PropertiesUtil.getKey("package.param");
        PATH_PO = PATH_BASE.replace(".", "/") + "/" + PATH_JAVA + "/" + PACKAGE_PO.replace(".", "/");
        PATH_PARAM = PATH_BASE.replace(".", "/") + "/" + PATH_JAVA + "/" + PACKAGE_PARAM.replace(".", "/");
        PACKAGE_UTILS = PACKAGE_BASE + "." + PropertiesUtil.getKey("package.utils");
        PATH_UTILS = PATH_BASE.replace(",", "/") + "/" + PATH_JAVA + "/" + PACKAGE_UTILS.replace(".", "/");
        PACKAGE_ENUMS = PACKAGE_BASE + "." + PropertiesUtil.getKey("package.enums");
        PATH_ENUMS = PATH_BASE.replace(",", "/") + "/" + PATH_JAVA + "/" + PACKAGE_ENUMS.replace(".", "/");

        AUTHOR = PropertiesUtil.getKey("author.comment");
    }

    public static void main(String[] args) {
        System.out.println(PACKAGE_PO);
        System.out.println(PATH_PO);
    }
}
