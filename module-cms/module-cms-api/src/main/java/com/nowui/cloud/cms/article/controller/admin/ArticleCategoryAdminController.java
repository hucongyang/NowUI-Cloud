package com.nowui.cloud.cms.article.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.nowui.cloud.cms.article.entity.ArticleCategory;
import com.nowui.cloud.cms.article.service.ArticleCategoryService;
import com.nowui.cloud.cms.navigation.entity.Navigation;
import com.nowui.cloud.constant.Constant;
import com.nowui.cloud.controller.BaseController;
import com.nowui.cloud.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 文章接口后台端控制器
 * 
 * @author marcus
 *
 * 2017年12月26日
 */
@Api(value = "文章分类", description = "文章分类后台端接口管理")
@RestController
public class ArticleCategoryAdminController extends BaseController {
    
    @Autowired
    private ArticleCategoryService articleCategoryService;
    
    @ApiOperation(value = "文章分类列表")
    @RequestMapping(value = "/article/category/admin/v1/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> listV1(@RequestBody ArticleCategory body) {
        validateRequest(
            body, 
            ArticleCategory.APP_ID, 
            ArticleCategory.ARTICLE_CATEGORY_NAME,
            ArticleCategory.ARTICLE_CATEGORY_CODE,
            ArticleCategory.PAGE_INDEX, 
            ArticleCategory.PAGE_SIZE
        );

        Integer resultTotal = articleCategoryService.countForAdmin(body.getAppId(), body.getArticleCategoryName(), body.getArticleCategoryCode());
        if (Util.isNullOrEmpty(body.getArticleCategoryName()) && Util.isNullOrEmpty(body.getArticleCategoryCode())) {
            
            List<Map<String, Object>> resultList = articleCategoryService.adminTreeList(body.getAppId(), body.getPageIndex(), body.getPageSize());

            validateResponse(ArticleCategory.ARTICLE_CATEGORY_ID, ArticleCategory.ARTICLE_CATEGORY_NAME, ArticleCategory.ARTICLE_CATEGORY_CODE, ArticleCategory.ARTICLE_CATEGORY_SORT, Constant.CHILDREN);

            return renderJson(resultTotal, resultList);

        } else {
            List<ArticleCategory> resultList = articleCategoryService.listForAdmin(body.getAppId(), body.getArticleCategoryName(), body.getArticleCategoryCode(), body.getPageIndex(), body.getPageSize());

            validateResponse(ArticleCategory.ARTICLE_CATEGORY_ID, ArticleCategory.ARTICLE_CATEGORY_NAME, ArticleCategory.ARTICLE_CATEGORY_CODE, ArticleCategory.ARTICLE_CATEGORY_SORT, Constant.CHILDREN);

            return renderJson(resultTotal, resultList);
        }

    }
    
    @ApiOperation(value = "文章分类树形列表")
    @RequestMapping(value = "/article/category/admin/v1/all/tree/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> allTreeListV1(@RequestBody ArticleCategory body) {
        validateRequest(
            body, 
            ArticleCategory.APP_ID
        );

        List<Map<String, Object>> resultList = articleCategoryService.adminAllTreeList(body.getAppId());

        validateResponse(ArticleCategory.ARTICLE_CATEGORY_ID, ArticleCategory.ARTICLE_CATEGORY_NAME, Constant.CHILDREN);

        return renderJson(resultList);
        
    }

    @ApiOperation(value = "根据编号查询文章分类信息")
    @RequestMapping(value = "/article/category/admin/v1/find", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> findV1(@RequestBody ArticleCategory body) {
        validateRequest(body, ArticleCategory.ARTICLE_CATEGORY_ID);

        ArticleCategory result = articleCategoryService.find(body.getArticleCategoryId());

        validateResponse(
                ArticleCategory.ARTICLE_CATEGORY_ID, 
                ArticleCategory.ARTICLE_CATEGORY_PARENT_ID, 
                ArticleCategory.ARTICLE_CATEGORY_NAME, 
                ArticleCategory.ARTICLE_CATEGORY_CODE, 
                ArticleCategory.ARTICLE_CATEGORY_KEYWORDS, 
                ArticleCategory.ARTICLE_CATEGORY_DESCRIPTION, 
                ArticleCategory.ARTICLE_CATEGORY_SORT, 
                ArticleCategory.SYSTEM_VERSION);

        return renderJson(result);
    }

    @ApiOperation(value = "文章分类新增")
    @RequestMapping(value = "/article/category/admin/v1/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> saveV1(@RequestBody ArticleCategory body) {
        validateRequest(
            body, 
            ArticleCategory.APP_ID, 
            ArticleCategory.ARTICLE_CATEGORY_PARENT_ID, 
            ArticleCategory.ARTICLE_CATEGORY_NAME, 
            ArticleCategory.ARTICLE_CATEGORY_CODE, 
            ArticleCategory.ARTICLE_CATEGORY_KEYWORDS, 
            ArticleCategory.ARTICLE_CATEGORY_DESCRIPTION
        );
        
        String articleCategoryParentPath = "";

        if (Util.isNullOrEmpty(body.getArticleCategoryParentId())) {

            JSONArray jsonArray = new JSONArray();

            articleCategoryParentPath = jsonArray.toJSONString();
        } else {
            ArticleCategory parent = articleCategoryService.find(body.getArticleCategoryParentId());

            JSONArray jsonArray = new JSONArray();;
            if (!Util.isNullOrEmpty(parent.getArticleCategoryParentPath())) {
                jsonArray = JSONArray.parseArray(parent.getArticleCategoryParentPath());
            } 
            jsonArray.add(parent.getArticleCategoryId());

            articleCategoryParentPath = jsonArray.toJSONString();
        }
        
        body.setArticleCategoryParentPath(articleCategoryParentPath);
        
        Boolean result = articleCategoryService.save(body, Util.getRandomUUID(), body.getSystemRequestUserId());

        return renderJson(result);
    }

    @ApiOperation(value = "文章分类修改")
    @RequestMapping(value = "/article/category/admin/v1/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateV1(@RequestBody ArticleCategory body) {
        validateRequest(
            body, 
            ArticleCategory.ARTICLE_CATEGORY_ID, 
            ArticleCategory.APP_ID, 
            ArticleCategory.ARTICLE_CATEGORY_PARENT_ID, 
            ArticleCategory.ARTICLE_CATEGORY_NAME, 
            ArticleCategory.ARTICLE_CATEGORY_CODE, 
            ArticleCategory.ARTICLE_CATEGORY_KEYWORDS, 
            ArticleCategory.ARTICLE_CATEGORY_DESCRIPTION, 
            ArticleCategory.SYSTEM_VERSION
        );

        Boolean result = articleCategoryService.update(body, body.getArticleCategoryId(), body.getSystemRequestUserId(), body.getSystemVersion());

        return renderJson(result);
    }

    @ApiOperation(value = "文章分类删除")
    @RequestMapping(value = "/article/category/admin/v1/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteV1(@RequestBody ArticleCategory body) {
        validateRequest(
            body, 
            ArticleCategory.ARTICLE_CATEGORY_ID, 
            ArticleCategory.SYSTEM_VERSION
        );

        Boolean result = articleCategoryService.delete(body.getArticleCategoryId(), body.getSystemRequestUserId(), body.getSystemVersion());

        return renderJson(result);
    }
    
    @ApiOperation(value = "文章分类重建缓存")
    @RequestMapping(value = "/article/category/admin/v1/replace", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> replaceV1(@RequestBody ArticleCategory body) {
        validateRequest(body, ArticleCategory.ARTICLE_CATEGORY_ID);

        articleCategoryService.replace(body.getArticleCategoryId());

        return renderJson(true);
    }

}
