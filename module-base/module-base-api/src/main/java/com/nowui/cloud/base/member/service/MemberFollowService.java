package com.nowui.cloud.base.member.service;
import com.nowui.cloud.service.BaseService;
import com.nowui.cloud.base.member.entity.MemberFollow;

import java.util.List;

/**
 * 会员关注业务接口
 *
 * @author marcus
 *
 * 2018-01-02
 */
public interface MemberFollowService extends BaseService<MemberFollow> {

    /**
     * 会员关注统计
     *
     * @param appId 应用编号
     * @param memberId 会员编号
     * @param userId 用户编号
     * @return Integer 会员关注统计
     */
    Integer adminCount(String appId, String memberId, String userId);

    /**
     * 会员关注列表
     *
     * @param appId 应用编号
     * @param memberId 会员编号
     * @param userId 用户编号
     * @param m 从m条开始
     * @param n 取n条数据
     * @return List<MemberFollow> 会员关注列表
     */
    List<MemberFollow> adminList(String appId, String memberId, String userId, Integer m, Integer n);
}