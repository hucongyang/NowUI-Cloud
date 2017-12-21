package com.nowui.cloud.app.app.service;

import java.util.List;

import com.nowui.cloud.app.app.entity.App;
import com.nowui.cloud.app.app.rpc.AppRpc;
import com.nowui.cloud.service.BaseService;

/**
 * 
 * @author marcus
 * @since 2017-12-20
 */
public interface AppService extends BaseService<App>, AppRpc {
    
    /**
     * 应用统计
     *
     * @param appName 应用名称
     * @return Integer 应用数量
     */
    Integer adminCount(String appName);

    /**
     * 应用列表
     *
     * @param appName 应用名称
     * @param m 从m条开始
     * @param n 取n条数据
     * @return List<App> 应用列表
     */
    List<App> adminList(String appName, Integer m, Integer n);

}
