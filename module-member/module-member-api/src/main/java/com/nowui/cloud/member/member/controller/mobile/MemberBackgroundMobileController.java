package com.nowui.cloud.member.member.controller.mobile;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nowui.cloud.base.user.entity.User;
import com.nowui.cloud.base.user.rpc.UserRpc;
import com.nowui.cloud.controller.BaseController;
import com.nowui.cloud.member.member.entity.MemberBackground;
import com.nowui.cloud.member.member.service.MemberBackgroundService;
import com.nowui.cloud.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 会员背景移动端控制器
 *
 * @author marcus
 *
 * 2018-01-14
 */
@Api(value = "会员背景", description = "会员背景移动端接口管理")
@RestController
public class MemberBackgroundMobileController extends BaseController {
    
    @Autowired
    private UserRpc userRpc;
    
    @Autowired
    private MemberBackgroundService memberBackgroundService;
    
    @ApiOperation(value = "更新会员背景")
    @RequestMapping(value = "/member/background/admin/v1/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateV1(@RequestBody MemberBackground body) {
        validateRequest(
                body,
                MemberBackground.APP_ID,
                MemberBackground.MEMBER_BACKGROUND,
                MemberBackground.SYSTEM_REQUEST_USER_ID
        );

        User user = userRpc.findV1(body.getSystemRequestUserId());
        //删除旧的会员背景
        memberBackgroundService.deleteByMemberId(user.getObjectId(), body.getSystemRequestUserId());
        
        body.setMemberId(user.getObjectId());
        //保存新的会员背景
        Boolean result = memberBackgroundService.save(body, Util.getRandomUUID(), body.getSystemRequestUserId());

        return renderJson(result);
    }

}