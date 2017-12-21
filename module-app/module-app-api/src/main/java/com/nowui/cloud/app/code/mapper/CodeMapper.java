package com.nowui.cloud.app.code.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 代码生成Mapper
 * 
 * @author marcus
 *
 */
@Mapper
@Component(value = "codeMapper")
public interface CodeMapper {
    
    public List<Map<String, Object>> selectTableListByTableSchema(@Param("tableSchema") String tableSchema, @Param("tableName") String tableName);
    
    public List<Map<String, Object>> selectTableFieldListByTableName(@Param("tableSchema") String tableSchema, @Param("tableName") String tableName);

}
