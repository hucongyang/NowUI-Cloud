package com.nowui.cloud.sns.topic.service.impl;

import com.nowui.cloud.mybatisplus.BaseWrapper;
import com.nowui.cloud.service.impl.BaseServiceImpl;
import com.nowui.cloud.sns.topic.entity.TopicComment;
import com.nowui.cloud.sns.topic.mapper.TopicCommentMapper;
import com.nowui.cloud.sns.topic.service.TopicCommentService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 话题评论业务实现
 *
 * @author xupengfei
 *
 * 2018-01-08
 */
@Service
public class TopicCommentServiceImpl extends BaseServiceImpl<TopicCommentMapper, TopicComment> implements TopicCommentService {

    @Override
    public Integer countForAdmin(String appId, String userId, String topicId, String topicCommentContent, String topicReplayUserId, String topicReplyCommentId) {
        Integer count = count(
                new BaseWrapper<TopicComment>()
                        .eq(TopicComment.APP_ID, appId)
                        .likeAllowEmpty(TopicComment.USER_ID, userId)
                        .likeAllowEmpty(TopicComment.TOPIC_ID, topicId)
                        .likeAllowEmpty(TopicComment.TOPIC_COMMENT_CONTENT, topicCommentContent)
                        .likeAllowEmpty(TopicComment.TOPIC_REPLAY_USER_ID, topicReplayUserId)
                        .likeAllowEmpty(TopicComment.TOPIC_REPLY_COMMENT_ID, topicReplyCommentId)
                        .eq(TopicComment.SYSTEM_STATUS, true)
        );
        return count;
    }

    @Override
    public List<TopicComment> listForAdmin(String appId, String userId, String topicId, String topicCommentContent, String topicReplayUserId, String topicReplyCommentId, Integer pageIndex, Integer pageSize) {
        List<TopicComment> topicCommentList = list(
                new BaseWrapper<TopicComment>()
                        .eq(TopicComment.APP_ID, appId)
                        .likeAllowEmpty(TopicComment.USER_ID, userId)
                        .likeAllowEmpty(TopicComment.TOPIC_ID, topicId)
                        .likeAllowEmpty(TopicComment.TOPIC_COMMENT_CONTENT, topicCommentContent)
                        .likeAllowEmpty(TopicComment.TOPIC_REPLAY_USER_ID, topicReplayUserId)
                        .likeAllowEmpty(TopicComment.TOPIC_REPLY_COMMENT_ID, topicReplyCommentId)
                        .eq(TopicComment.SYSTEM_STATUS, true)
                        .orderDesc(Arrays.asList(TopicComment.SYSTEM_CREATE_TIME)),
                pageIndex,
                pageSize
        );

        return topicCommentList;
    }

	@Override
	public List<TopicComment> allCommentList(String appId, String userId, String topicId) {
		List<TopicComment> topicCommentList = list(
                new BaseWrapper<TopicComment>()
                        .eq(TopicComment.APP_ID, appId)
                        .likeAllowEmpty(TopicComment.USER_ID, userId)
                        .likeAllowEmpty(TopicComment.TOPIC_ID, topicId)
                        .eq(TopicComment.SYSTEM_STATUS, true)
                        .orderDesc(Arrays.asList(TopicComment.SYSTEM_CREATE_TIME))
        );

        return topicCommentList;
	}

}