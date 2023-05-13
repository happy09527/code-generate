package com.exam.generate.bean;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ZhangX
 * @createDate: 2023/5/13
 * @description:
 */

public class TableInfo {
    //    表明
    private String tableName;
    //    参数信息
    private String beanName;
    //    参数名称
    private String beanParamName;
    //    备注
    private String comment;
    //    字段信息
    private List<FieldInfo> fields;
    //    唯一索引
    private Map<String, List<FieldInfo>> keyIndex = new LinkedHashMap<>();
    //    是否有日期
    private boolean haveDate;
    //    是否有时间类型
    private boolean haveDateTime;

    //    是否有高精度小数
    private boolean haveBigDecimal;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanParamName() {
        return beanParamName;
    }

    public void setBeanParamName(String beanParamName) {
        this.beanParamName = beanParamName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<FieldInfo> getFields() {
        return fields;
    }

    public void setFields(List<FieldInfo> fields) {
        this.fields = fields;
    }

    public Map<String, List<FieldInfo>> getKeyIndex() {
        return keyIndex;
    }

    public void setKeyIndex(Map<String, List<FieldInfo>> keyIndex) {
        this.keyIndex = keyIndex;
    }

    public boolean isHaveDate() {
        return haveDate;
    }

    public void setHaveDate(boolean haveDate) {
        this.haveDate = haveDate;
    }

    public boolean isHaveDateTime() {
        return haveDateTime;
    }

    public void setHaveDateTime(boolean haveDateTime) {
        this.haveDateTime = haveDateTime;
    }

    public boolean isHaveBigDecimal() {
        return haveBigDecimal;
    }

    public void setHaveBigDecimal(boolean haveBigDecimal) {
        this.haveBigDecimal = haveBigDecimal;
    }

    @Override
    public String toString() {
        return "TableInfo{" +
                "tableName='" + tableName + '\'' +
                ", beanName='" + beanName + '\'' +
                ", beanParamName='" + beanParamName + '\'' +
                ", comment='" + comment + '\'' +
                ", fields=" + fields +
                ", keyIndex=" + keyIndex +
                ", haveDate=" + haveDate +
                ", haveDateTime=" + haveDateTime +
                ", haveBigDecimal=" + haveBigDecimal +
                '}';
    }
}
