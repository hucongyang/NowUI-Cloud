package com.nowui.cloud.sns.topic.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.nowui.cloud.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * 话题评论
 *
 * @author xupengfei
 *
 * 2018-01-08
 */
@Component
@Document(indexName = "nowui", type = "topic_comment_info")
@TableName(value = "topic_comment_info")
public class TopicComment extends BaseEntity {

    /**
     * 话题评论id
     */
	@Id
    @TableId
    @NotNull(message = "话题评论id不能为空")
    @Length(max = 32, message = "话题评论id长度超出限制")
    private String topicCommentId;
    public static final String TOPIC_COMMENT_ID = "topicCommentId";

    /**
     * 应用Id
     */
    @Field
    @TableField
    @NotNull(message = "应用Id不能为空")
    @Length(max = 32, message = "应用Id长度超出限制")
    private String appId;
    public static final String APP_ID = "appId";

    /**
     * 用户id
     */
    @Field
    @TableField
    @NotNull(message = "用户id不能为空")
    @Length(max = 32, message = "用户id长度超出限制")
    private String userId;
    public static final String USER_ID = "userId";

    /**
     * 用户id
     */
    @Field
    @TableField
    @NotNull(message = "话题id不能为空")
    @Length(max = 32, message = "话题id长度超出限制")
    private String topicId;
    public static final String TOPIC_ID = "topicId";
    
    /**
     * 话题评论内容
     */
    @Field
    @TableField
    @NotNull(message = "话题评论内容不能为空")
    @Length(max = 255, message = "话题评论内容长度超出限制")
    private String topicCommentContent;
    public static final String TOPIC_COMMENT_CONTENT = "topicCommentContent";

    /**
     * 回复人
     */
    @Field
    @TableField
    @NotNull(message = "回复人不能为空")
    @Length(max = 32, message = "回复人长度超出限制")
    private String topicReplayUserId;
    public static final String TOPIC_REPLAY_USER_ID = "topicReplayUserId";
    
    public static final String TOPIC_REPLAY_USER_NICK_NAME = "topicReplayUserNickName";

    /**
     * 被回复评论id
     */
    @Field
    @TableField
    @NotNull(message = "被回复评论id不能为空")
    @Length(max = 32, message = "被回复评论id长度超出限制")
    private String topicReplyCommentId;
    public static final String TOPIC_REPLY_COMMENT_ID = "topicReplyCommentId";


    public String getTopicCommentId() {
        return getString(TOPIC_COMMENT_ID);
    }
    
    public void setTopicCommentId(String topicCommentId) {
        put(TOPIC_COMMENT_ID, topicCommentId);
    }

    public String getAppId() {
        return getString(APP_ID);
    }
    
    public void setAppId(String appId) {
        put(APP_ID, appId);
    }

    public String getUserId() {
        return getString(USER_ID);
    }
    
    public void setUserId(String userId) {
        put(USER_ID, userId);
    }
    
    public String getTopicId() {
        return getString(TOPIC_ID);
    }
    
    public void setTopicId(String topicId) {
        put(TOPIC_ID, topicId);
    }

    public String getTopicCommentContent() {
        return getString(TOPIC_COMMENT_CONTENT);
    }
    
    public void setTopicCommentContent(String topicCommentContent) {
        put(TOPIC_COMMENT_CONTENT, topicCommentContent);
    }

    public String getTopicReplayUserId() {
        return getString(TOPIC_REPLAY_USER_ID);
    }
    
    public void setTopicReplayUserId(String topicReplayUserId) {
        put(TOPIC_REPLAY_USER_ID, topicReplayUserId);
    }

    public String getTopicReplyCommentId() {
        return getString(TOPIC_REPLY_COMMENT_ID);
    }
    
    public void setTopicReplyCommentId(String topicReplyCommentId) {
        put(TOPIC_REPLY_COMMENT_ID, topicReplyCommentId);
    }


}