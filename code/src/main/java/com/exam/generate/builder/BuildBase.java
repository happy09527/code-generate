package com.exam.generate.builder;

import com.exam.generate.bean.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author: ZhangX
 * @createDate: 2023/5/17
 * @description: 构建基础类。通过读取txt文件进行
 */
public class BuildBase {
    private static Logger logger = LoggerFactory.getLogger(BuildBase.class);
    public static void execute(){
        build("DateTimePatternEnum",Constants.PATH_ENUMS,Constants.PACKAGE_ENUMS);
        build("DateUtil", Constants.PATH_UTILS,Constants.PACKAGE_UTILS);
    }

    private static void build(String fileName , String outputPath,String packageName){
        File folder = new File(outputPath);
        if(!folder.exists()){
            folder.mkdirs();
        }

        File javaFile = new File(outputPath,fileName+".java");
        OutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try{
            os = new FileOutputStream(javaFile);
            osw = new OutputStreamWriter(os,"utf-8");
            bw = new BufferedWriter(osw);
            String templatePath = BuildBase.class.getClassLoader().getResource("template/" + fileName+".txt").getPath();

            is = new FileInputStream(templatePath);
            isr = new InputStreamReader(is,"utf-8");
            br = new BufferedReader(isr);
            bw.write("package "+packageName + ";\n\n");
            String lineInfo = null;
            while((lineInfo = br.readLine())!=null){
                bw.write(lineInfo+"\n");
            }
            bw.flush();

        }catch (IOException e){
            logger.error(String.format("生成基础类%s失败", fileName),e);
        }finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(isr!=null){
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(osw!=null){
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
