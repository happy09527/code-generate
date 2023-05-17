package com.exam.generate.builder;

import com.exam.generate.bean.Constants;
import com.exam.generate.bean.FieldInfo;
import com.exam.generate.bean.TableInfo;
import com.exam.generate.utils.MyStringUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: ZhangX
 * @createDate: 2023/5/15
 * @description: 构建实例类
 */


public class BuildPo {
    private static Logger logger = LoggerFactory.getLogger(BuildPo.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_PO);
        if (!folder.exists()) {
            folder.mkdirs();
        }
//        生成文件
//        File file = new File(folder,tableInfo.getBeanName()+".java");
//        System.out.println(file.getAbsolutePath());
//        try{
//            file.createNewFile();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
        OutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        File file = new File(folder, tableInfo.getBeanName() + ".java");
        try {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os, "utf8");
            bw = new BufferedWriter(osw);
            bw.write("package " + Constants.PACKAGE_PO + ";");
            bw.newLine();
            bw.newLine();
            // 导包
            bw.write("import java.io.Serializable;");
            bw.newLine();
            boolean ignoreField = false;
            for (FieldInfo fieldInfo : tableInfo.getFields()) {
                if (ArrayUtils.contains(Constants.IGNORE_BEAN_FIELD.split(","), fieldInfo.getPropertyName())) {
                    ignoreField = true;
                }
            }
            if (ignoreField) {
                bw.write(Constants.IGNORE_BEAN_CLASS);
                bw.newLine();
            }

            if (tableInfo.isHaveDate() || tableInfo.isHaveDateTime()) {
                bw.write("import "+ Constants.PACKAGE_ENUMS +".DateTimePatternEnum;\nimport "+ Constants.PACKAGE_UTILS+".DateUtil;\n");
                bw.write("import java.util.Date;");
                bw.newLine();
                bw.write(Constants.BEAN_DATA_FORMAT_CLASS);
                bw.newLine();
                bw.write(Constants.BEAN_DATA_UNFORMAT_CLASS);
                bw.newLine();
            }
            if (tableInfo.isHaveBigDecimal()) {
                bw.write("import java.math.BigDecimal;");
                bw.newLine();
            }
            bw.newLine();
            BuildComment.createClassComment(bw, tableInfo.getComment());
            bw.newLine();
            bw.write("public class " + tableInfo.getBeanName() + " implements Serializable {");
            bw.newLine();
            bw.newLine();
            // 获取并写入属性
            for (FieldInfo fieldInfo : tableInfo.getFields()) {
                BuildComment.createFieldComment(bw, fieldInfo.getComment());
                bw.newLine();
                if (ArrayUtils.contains(Constants.IGNORE_BEAN_FIELD.split(","), fieldInfo.getPropertyName())) {
                    bw.write("\t" + Constants.IGNORE_BEAN_EXPRESSION);
                    bw.newLine();
                }
                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, fieldInfo.getSqlType())) {
                    bw.write("\t" + String.format(Constants.BEAN_DATA_FORMAT_EXPRESSION, "yyyy-MM-dd HH:mm:ss"));
                    bw.newLine();
                    bw.write("\t" + String.format(Constants.BEAN_DATA_UNFORMAT_EXPRESSION, "yyyy-MM-dd HH:mm:ss"));
                    bw.newLine();
                }
                if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType())) {
                    bw.write("\t" + String.format(Constants.BEAN_DATA_FORMAT_EXPRESSION, "yyyy-MM-dd"));
                    bw.newLine();
                    bw.write("\t" + String.format(Constants.BEAN_DATA_UNFORMAT_EXPRESSION, "yyyy-MM-dd"));
                    bw.newLine();
                }
                bw.write("\tprivate " + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName() + ";");
                bw.newLine();
                bw.newLine();
            }
            // 生成get set toString方法
            for (FieldInfo fieldInfo : tableInfo.getFields()) {
                String tempField = MyStringUtil.upCaseString(fieldInfo.getPropertyName());
                bw.write("\tpublic void set"+tempField+"(" + fieldInfo.getJavaType() + " "+fieldInfo.getPropertyName() + ") {" );
                bw.newLine();
                bw.write("\t\tthis."+fieldInfo.getPropertyName()+" = "+fieldInfo.getPropertyName() + ";");
                bw.newLine();
                bw.write("\t}\n\n");

                bw.write("\tpublic "+fieldInfo.getJavaType()+" get"+tempField+"() {");
                bw.newLine();
                bw.write("\t\treturn "+fieldInfo.getPropertyName()+";");
                bw.newLine();
                bw.write("\t}\n");
                bw.newLine();
            }
            // toString 方法
            StringBuffer sb = new StringBuffer();
            int index = 0;
            for(FieldInfo fieldInfo : tableInfo.getFields()){
                index++;
                String propertyName = fieldInfo.getPropertyName();
                if(ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES,fieldInfo.getSqlType())){
                    propertyName = "DateUtil.format("+ propertyName + ", DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())";
                }else if(ArrayUtils.contains(Constants.SQL_DATE_TYPES,fieldInfo.getSqlType())){
                    propertyName = "DateUtil.format("+ propertyName + ", DateTimePatternEnum.YYYY_MM_DD.getPattern())";
                }
                sb.append(fieldInfo.getComment() + ":\" + ("+fieldInfo.getPropertyName()+" == null ? \"空\" : "+propertyName+")");
                if(index < tableInfo.getFields().size()){
                    sb.append(" + ").append("\", ");
                }
            }
            bw.write("\t@Override\n\tpublic String toString(){\n"+"\t\treturn \"" + sb + ";\n\t");
            bw.write("}\n");
            bw.write("}");
        } catch (Exception e) {
            logger.error("创建po文件失败", e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
