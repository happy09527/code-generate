package com.exam.generate.builder;

import com.exam.generate.bean.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: ZhangX
 * @createDate: 2023/5/15
 * @description: 生成注释 包括类注释与属性注释
 */
public class BuildComment {
    private static Logger logger = LoggerFactory.getLogger(BuildComment.class);

    public static void createClassComment(BufferedWriter bw, String comment) {
        try {
            bw.write("/**");
            bw.newLine();
            bw.write(" * @author: " + Constants.AUTHOR);
            bw.newLine();
            bw.write(" * @date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
            bw.newLine();
            bw.write(" * @description: " + comment);
            bw.newLine();
            bw.write(" */");
        } catch (IOException e) {
            logger.error("类注释写入错误" + e);
        }
    }

    public static void createFieldComment(BufferedWriter bw, String comment) {
        try {
            bw.write("\t/**");
            bw.newLine();
            bw.write(" \t* @description: " + comment);
            bw.newLine();
            bw.write(" \t*/");
        } catch (IOException e) {
            logger.error("属性注释写入错误" + e);
        }
    }

    public static void createMethodComment(BufferedWriter bw, String comment) {

    }
}
