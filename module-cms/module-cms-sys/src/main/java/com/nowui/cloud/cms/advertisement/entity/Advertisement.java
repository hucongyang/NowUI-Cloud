package com.nowui.cloud.cms.advertisement.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.nowui.cloud.entity.BaseEntity;

/**
 * 广告
 * 
 * @author marcus
 *
 * 2017年12月26日
 */
@Component(value = "advertisement")
@Document(indexName = "nowui", type = "advertisement_info")
@TableName(value = "advertisement_info")
public class Advertisement extends BaseEntity {
    
    /**
     * 广告编号
     */
    @Id
    @TableId
    @NotNull(message = "广告编号不能为空")
    @Length(max = 32, message = "广告编号长度超出限制")
    private String advertisementId;
    public static final String ADEVERTISEMENT_ID = "advertisementId";
    
    /**
     * 应用编号
     */
    @Field
    @TableField
    @NotNull(message = "应用编号不能为空")
    @Length(max = 32, message = "应用编号长度超出限制")
    private String appId;
    public static final String APP_ID = "appId";
    
    /**
     * 广告分类编码
     */
    @Field
    @TableField
    @NotNull(message = "广告分类编码不能为空")
    @Length(max = 50, message = "广告分类编码长度超出限制")
    private String advertisementCategoryCode;
    public static final String ADEVERTISEMENT_CATEGORY_CODE = "advertisementCategoryCode";
    
    /**
     * 广告编码
     */
    @Field
    @TableField
    @NotNull(message = "广告编码不能为空")
    @Length(max = 50, message = "广告编码长度超出限制")
    private String advertisementCode;
    public static final String ADEVERTISEMENT_CODE = "advertisementCode";
    
    /**
     * 广告标题
     */
    @Field
    @TableField
    @NotNull(message = "广告标题不能为空")
    @Length(max = 200, message = "广告标题长度超出限制")
    private String advertisementTitle;
    public static final String ADEVERTISEMENT_TITLE = "advertisementTitle";
    
    /**
     * 广告图片
     */
    @Field
    @TableField
    @NotNull(message = "广告图片不能为空")
    @Length(max = 200, message = "广告图片长度超出限制")
    private String advertisementImage;
    public static final String ADEVERTISEMENT_IMAGE = "advertisementImage";
    
    /**
     * 广告内容
     */
    @Field
    @TableField
    @NotNull(message = "广告内容不能为空")
    @Length(max = 2000, message = "广告内容长度超出限制")
    private String advertisementContent;
    public static final String ADEVERTISEMENT_CONTENT = "advertisementContent";
    
    /**
     * 广告位置
     */
    @Field
    @TableField
    @NotNull(message = "广告位置不能为空")
    @Length(max = 200, message = "广告位置长度超出限制")
    private String advertisementPosition;
    public static final String ADEVERTISEMENT_POSITION = "advertisementPosition";
    
    /**
     * 广告超链接
     */
    @Field
    @TableField
    @NotNull(message = "广告超链接不能为空")
    @Length(max = 200, message = "广告超链接长度超出限制")
    private String advertisementLink;
    public static final String ADEVERTISEMENT_LINK = "advertisementLink";
    
    /**
     * 广告是否失效
     */
    @Field
    @TableField
    @NotNull(message = "广告是否失效不能为空")
    private Boolean advertisementIsEfficient;
    public static final String ADEVERTISEMENT_IS_EFFICIENT = "advertisementIsEfficient";
    
    /**
     * 广告排序
     */
    @Field
    @TableField
    @NotNull(message = "广告排序不能为空")
    private Boolean advertisementSort;
    public static final String ADEVERTISEMENT_SORT = "advertisementSort";
    
    public String getAdvertisementId() {
        return getString(ADEVERTISEMENT_ID);
    }
    
    public void setAdvertisementId(String advertisementId) {
        put(ADEVERTISEMENT_ID, advertisementId);
    }
    
    public String getAppId() {
        return getString(APP_ID);
    }
    
    public void setAppId(String appId) {
        put(APP_ID, appId);
    }
    
    public String getAdvertisementCategoryCode() {
        return getString(ADEVERTISEMENT_CATEGORY_CODE);
    }
    
    public void setAdvertisementCategoryCode(String advertisementCategoryCode) {
        put(ADEVERTISEMENT_CATEGORY_CODE, advertisementCategoryCode);
    }
    
    public String getAdvertisementCode() {
        return getString(ADEVERTISEMENT_CODE);
    }
    
    public void setAdvertisementCode(String advertisementCode) {
        put(ADEVERTISEMENT_CODE, advertisementCode);
    }
    
    public String getAdvertisementTitle() {
        return getString(ADEVERTISEMENT_TITLE);
    }
    
    public void setAdvertisementTitle(String advertisementTitle) {
        put(ADEVERTISEMENT_TITLE, advertisementTitle);
    }
    
    public String getAdvertisementImage() {
        return getString(ADEVERTISEMENT_IMAGE);
    }
    
    public void setAdvertisementImage(String advertisementImage) {
        put(ADEVERTISEMENT_IMAGE, advertisementImage);
    }
    
    public String getAdvertisementContent() {
        return getString(ADEVERTISEMENT_CONTENT);
    }
    
    public void setAdvertisementContent(String advertisementContent) {
        put(ADEVERTISEMENT_CONTENT, advertisementContent);
    }
    
    public String getAdvertisementPosition() {
        return getString(ADEVERTISEMENT_POSITION);
    }
    
    public void setAdvertisementPosition(String advertisementPosition) {
        put(ADEVERTISEMENT_POSITION, advertisementPosition);
    }
    
    public String getAdvertisementLink() {
        return getAdvertisementLink();
    }
    
    public void setAdvertisementLink(String advertisementLink) {
        put(ADEVERTISEMENT_LINK, advertisementLink);
    }
    
    public Boolean getAdvertisementIsEfficient() {
        return getBoolean(ADEVERTISEMENT_IS_EFFICIENT);
    }
    
    public void setAdvertisementIsEfficient(Boolean advertisementIsEfficient) {
        put(ADEVERTISEMENT_IS_EFFICIENT, advertisementIsEfficient);
    }
    
    public Boolean getAdvertisementSort() {
        return getBoolean(ADEVERTISEMENT_SORT);
    }
    
    public void setAdvertisementSort(Boolean advertisementSort) {
        put(ADEVERTISEMENT_SORT, advertisementSort);
    }

}
