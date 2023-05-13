package com.exam.generate.builder;

import com.exam.generate.bean.Constants;
import com.exam.generate.bean.FieldInfo;
import com.exam.generate.bean.TableInfo;
import com.exam.generate.utils.MyStringUtils;
import com.exam.generate.utils.PropertiesUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: ZhangX
 * @createDate: 2023/5/13
 * @description:
 */

public class BuilderTable {
    private static Logger logger = LoggerFactory.getLogger(BuilderTable.class);
    private static Connection connection = null;
    private static String SQL_SHOW_TABLE_STATUS = "show table status";

    private static String SQL_SHOW_TABLE_FIELDS = "show full fields from %s";

    static {
        String driverName = PropertiesUtil.getKey("spring.datasource.driver-class-name");
        String url = PropertiesUtil.getKey("spring.datasource.url");
        String user = PropertiesUtil.getKey("spring.datasource.data-username");
        String password = PropertiesUtil.getKey("spring.datasource.data-password");
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            logger.error("数据库连接失败", e);
        }
    }

    public static List<TableInfo> getTables() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<TableInfo> tables = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SQL_SHOW_TABLE_STATUS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String tableName = resultSet.getString("name");
                String comment = resultSet.getString("comment");
                String beanName = tableName;
                if (Constants.IGNORE_TABLE_PREFIX.length() != 0) {
                    beanName = processField(tableName.substring(Constants.IGNORE_TABLE_PREFIX.length() + 1), true);
                }
                TableInfo table = new TableInfo();
                table.setTableName(tableName);
                table.setBeanName(beanName);
                table.setComment(comment);
                table.setBeanParamName(beanName);
                table.setFields(getFields(table));
                logger.info(table.toString());
            }
        } catch (SQLException e) {
            logger.error("查询表结构失败", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return tables;
    }

    private static List<FieldInfo> getFields(TableInfo tableInfo) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<FieldInfo> fieldInfos = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(String.format(SQL_SHOW_TABLE_FIELDS, tableInfo.getTableName()));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String field = resultSet.getString("field");
                String type = resultSet.getString("type");
                String extra = resultSet.getString("extra");
                String comment = resultSet.getString("comment");
                if (type.indexOf('(') > 0) {
                    type = type.substring(0, type.indexOf('('));
                }
                String propertyName = processField(field, false);

                FieldInfo fieldInfo = new FieldInfo();
                fieldInfo.setFieldName(field);
                fieldInfo.setComment(comment);
                fieldInfo.setSqlType(type);
                fieldInfo.setAutoIncrement("auto_increment".equalsIgnoreCase(extra));
                fieldInfo.setPropertyName(propertyName);
                fieldInfo.setJavaType(processJavaType(type));
                fieldInfos.add(fieldInfo);
                if (ArrayUtils.contains(Constants.SQL_DECIMAL_TYPE, type)) {
                    tableInfo.setHaveBigDecimal(true);
                }
                if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, type)) {
                    tableInfo.setHaveDate(true);
                }
                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, type)) {
                    tableInfo.setHaveDateTime(true);
                }
            }
        } catch (Exception e) {
            logger.error("查询表属性失败", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return fieldInfos;
    }

    private static String processJavaType(String type) {
        if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, type) || ArrayUtils.contains(Constants.SQL_DATE_TYPES, type)) {
            return "Date";
        } else if (ArrayUtils.contains(Constants.SQL_LONG_TYPE, type)) {
            return "Long";
        } else if (ArrayUtils.contains(Constants.SQL_DECIMAL_TYPE, type)) {
            return "BigDecimal";
        } else if (ArrayUtils.contains(Constants.SQL_INTEGER_TYPE, type)) {
            return "Integer";
        } else if (ArrayUtils.contains(Constants.SQL_STRING_TYPE, type)) {
            return "String";
        } else {
            throw new RuntimeException("无法识别的类型," + type);
        }
    }

    private static String processField(String field, boolean upCaseField) {
        StringBuffer stringBuffer = new StringBuffer();
        String[] fields = field.split("_");
        if (upCaseField) {
            stringBuffer.append(MyStringUtils.upCaseString(fields[0]));
        } else {
            stringBuffer.append(fields[0]);
        }
        for (int i = 1; i < fields.length; i++) {
            stringBuffer.append(MyStringUtils.upCaseString(fields[i]));
        }
        return stringBuffer.toString();
    }
}
