package com.exam.generate;

import com.exam.generate.bean.TableInfo;
import com.exam.generate.builder.BuildBase;
import com.exam.generate.builder.BuildPo;
import com.exam.generate.builder.BuilderTable;

import java.util.List;

/**
 * @author: ZhangX
 * @createDate: 2023/5/13
 * @description:
 */

public class GenApplication {
    public static void main(String[] args) {
        List<TableInfo> tables =  BuilderTable.getTables();
        BuildBase.execute();
        for(TableInfo tableInfo : tables){
            BuildPo.execute(tableInfo);
        }
    }
}
