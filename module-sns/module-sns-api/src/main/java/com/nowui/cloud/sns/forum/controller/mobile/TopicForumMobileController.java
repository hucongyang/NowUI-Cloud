package com.nowui.cloud.sns.forum.controller.mobile;

import com.nowui.cloud.controller.BaseController;
import com.nowui.cloud.sns.forum.entity.TopicForum;
import com.nowui.cloud.sns.forum.service.TopicForumService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 话题论坛关联移动端控制器
 *
 * @author xupengfei
 *
 * 2018-01-08
 */
@Api(value = "话题论坛关联", description = "话题论坛关联移动端接口管理")
@RestController
public class TopicForumMobileController extends BaseController {
	
	@Autowired
    private TopicForumService topicForumService;

    @ApiOperation(value = "话题论坛关联列表")
    @RequestMapping(value = "/topic/forum/mobile/v1/list", method = {RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> listV1(@RequestBody TopicForum body) {
        validateRequest(
                body,
                TopicForum.APP_ID,
                TopicForum.FORUM_ID,
                TopicForum.TOPIC_ID,
                TopicForum.PAGE_INDEX,
                TopicForum.PAGE_SIZE
        );

        Integer resultTotal = topicForumService.countForAdmin(body.getAppId() , body.getForumId(), body.getTopicId());
        List<TopicForum> resultList = topicForumService.listForAdmin(body.getAppId(), body.getForumId(), body.getTopicId(), body.getPageIndex(), body.getPageSize());

        validateResponse(
                TopicForum.TOPIC_FORUM_MAP_ID,
                TopicForum.FORUM_ID,
                TopicForum.TOPIC_ID
        );

        return renderJson(resultTotal, resultList);
    }

}