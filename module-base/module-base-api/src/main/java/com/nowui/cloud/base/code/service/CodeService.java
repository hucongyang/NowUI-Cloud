package com.nowui.cloud.base.code.service;

import com.nowui.cloud.base.code.entity.Code;
import com.nowui.cloud.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 代码生成service接口
 * 
 * @author marcus
 *
 */

public interface CodeService extends BaseService<Code> {
    
    /**
     * 查询数据库表列表
     * @param tableSchema 数据库名称
     * @param tableName 数据表名称
     * @return
     */
    List<Code> tableSchemaList(String tableSchema, String tableName);
    
    /**
     * 查询数据库表字段列表
     * @param tableSchema 数据库名称
     * @param tableName 数据表名称
     * @return
     */
    List<Code> tableNameList(String tableSchema, String tableName);


}
