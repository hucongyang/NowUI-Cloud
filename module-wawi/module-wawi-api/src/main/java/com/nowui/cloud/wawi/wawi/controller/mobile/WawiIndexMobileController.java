package com.nowui.cloud.wawi.wawi.controller.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nowui.cloud.base.app.entity.App;
import com.nowui.cloud.base.file.entity.File;
import com.nowui.cloud.cms.advertisement.entity.Advertisement;
import com.nowui.cloud.cms.advertisement.rpc.AdvertisementRpc;
import com.nowui.cloud.cms.article.entity.Article;
import com.nowui.cloud.cms.article.rpc.ArticleRpc;
import com.nowui.cloud.cms.navigation.entity.Navigation;
import com.nowui.cloud.cms.navigation.rpc.NavigationRpc;
import com.nowui.cloud.cms.toolbar.entity.Toolbar;
import com.nowui.cloud.cms.toolbar.rpc.ToolbarRpc;
import com.nowui.cloud.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 哇伊首页移动端控制器
 * 
 * @author marcus
 *
 * 2018年1月15日
 */
@Api(value = "哇伊首页", description = "哇伊首页移动端接口管理")
@RestController
public class WawiIndexMobileController extends BaseController {
    
    @Autowired
    private AdvertisementRpc advertisementRpc;
    
    @Autowired
    private NavigationRpc navigationRpc;
    
    @Autowired
    private ToolbarRpc toolbarRpc;
    
    @Autowired
    private ArticleRpc articleRpc;
    
    @ApiOperation(value = "哇伊首页初始数据")
    @RequestMapping(value = "/wawi/mobile/v1/index/init", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> indexInitV1(@RequestBody App body) {
        validateRequest(
            body, 
            App.APP_ID
        );

        // 获取首页BANNER列表
        List<Advertisement> indexBannerList = advertisementRpc.listByCategoryCodeV1(body.getAppId(), "INDEX_BANNER");
        
        for (Advertisement advertisement : indexBannerList) {
            advertisement.keep(
                    Advertisement.ADEVERTISEMENT_ID,
                    File.FILE_PATH,
                    Advertisement.ADEVERTISEMENT_LINK,
                    Advertisement.ADEVERTISEMENT_TITLE
            );
        }
        
        // 获取首页导航栏列表
        List<Navigation> indexNavigationList = navigationRpc.listByCategoryCodeV1(body.getAppId(), "INDEX");
        
        for (Navigation navigation : indexNavigationList) {
            navigation.keep(
                    Navigation.NAVIGATION_ID,
                    File.FILE_PATH,
                    Navigation.NAVIGATION_NAME,
                    Navigation.NAVIGATION_URL
            );
        }
        
        // 获取首页工具栏列表
        List<Toolbar> toolbarList = toolbarRpc.list(body.getAppId());
        
        for (Toolbar toolbar : toolbarList) {
            toolbar.keep(
                    Toolbar.TOOLBAR_ID,
                    File.FILE_PATH,
                    Toolbar.TOOLBAR_NAME
            );
        }
        
        // 宠物分类列表
        List<Article> petCategoryList = articleRpc.listByCategoryCodeV1(body.getAppId(), "PET_CATEGORY");
        
        for (Article article : petCategoryList) {
            article.keep(
                    Article.ARTICLE_ID, 
                    File.FILE_PATH,
                    Article.ARTICLE_TITLE
            );
        }
        
        // 猜你喜欢列表 随机取5条 TODO
        List<Article> recommendList = new ArrayList<>();
        
        // 热门话题 置顶加点击数
        List<Article> hotList = new ArrayList<>();
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        result.put("indexBannerList", indexBannerList);
        result.put("indexNavigationList", indexNavigationList);
        result.put("toolbarList", toolbarList);
        result.put("petCategoryList", petCategoryList);
        result.put("recommendList", recommendList);
        result.put("hotList", hotList);
        
        validateResponse(
                "indexBannerList", 
                "indexNavigationList",
                "toolbarList",
                "petCategoryList",
                "recommendList",
                "hotList"
        );
        return renderJson(result);
    }
    

}
