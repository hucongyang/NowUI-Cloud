package com.nowui.cloud.base.user.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nowui.cloud.base.user.entity.UserAvatar;
import com.nowui.cloud.base.user.mapper.UserAvatarMapper;
import com.nowui.cloud.base.user.service.UserAvatarService;
import com.nowui.cloud.mybatisplus.BaseWrapper;
import com.nowui.cloud.service.impl.BaseServiceImpl;

/**
 * 用户头像业务实现
 *
 * @author marcus
 *
 * 2018-01-08
 */
@Service
public class UserAvatarServiceImpl extends BaseServiceImpl<UserAvatarMapper, UserAvatar> implements UserAvatarService {

    @Override
    public UserAvatar findByUserId(String userId) {
        UserAvatar userAvatar = find(
                new BaseWrapper<UserAvatar>()
                    .eq(UserAvatar.USER_ID, userId)
                    .eq(UserAvatar.SYSTEM_STATUS, true)
                    .orderDesc(Arrays.asList(UserAvatar.SYSTEM_CREATE_TIME))
        );
        
        return userAvatar;
    }

    @Override
    public void deleteByUserId(String userId, String systemUpdateUserId) {
        List<UserAvatar> userAvatarList = list(
                new BaseWrapper<UserAvatar>()
                        .eq(UserAvatar.USER_ID, userId)
                        .eq(UserAvatar.SYSTEM_STATUS, true)
                        .orderDesc(Arrays.asList(UserAvatar.SYSTEM_CREATE_TIME))
        );
        if (userAvatarList != null && userAvatarList.size() > 0) {
            for (UserAvatar userAvatar : userAvatarList) {
                delete(userAvatar.getUserAvatarId(), systemUpdateUserId, userAvatar.getSystemVersion());
            }
        }
    }

}