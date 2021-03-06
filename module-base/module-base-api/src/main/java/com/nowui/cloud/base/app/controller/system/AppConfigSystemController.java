package com.nowui.cloud.base.app.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.nowui.cloud.base.app.entity.AppConfig;
import com.nowui.cloud.base.app.entity.AppConfigCategory;
import com.nowui.cloud.base.app.rpc.AppConfigRpc;
import com.nowui.cloud.base.app.service.AppConfigCategoryService;
import com.nowui.cloud.base.app.service.AppConfigService;

import io.swagger.annotations.Api;

/**
 * 应用配置系统端控制器
 *
 * @author marcus
 *
 * 2018-01-01
 */
@Api(value = "应用配置", description = "应用配置系统端接口管理")
@RestController
public class AppConfigSystemController implements AppConfigRpc {
    
    @Autowired
    private AppConfigCategoryService appConfigCategoryService;
    
    @Autowired
    private AppConfigService appConfigService;

    @Override
    public JSONObject findByCategoryCode(String appId, String appConfigCategoryCode) {
        AppConfigCategory appConfigCategory = appConfigCategoryService.findByConfigCategoryCode(appId, appConfigCategoryCode);
        if (appConfigCategory == null) {
            return null;
        }
        List<AppConfig> appConfigList = appConfigService.abledListByConfigCategoryId(appId, appConfigCategory.getConfigCategoryId());
        if (appConfigList == null || appConfigList.size() == 0) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        for (AppConfig appConfig : appConfigList) {
            jsonObject.put(appConfig.getConfigKey(), appConfig.getConfigValue());
        }
        return jsonObject;
    }

}
