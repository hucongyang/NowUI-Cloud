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
import com.nowui.cloud.member.member.entity.MemberSignature;
import com.nowui.cloud.member.member.service.MemberSignatureService;
import com.nowui.cloud.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 	会员签名移动端控制器
 *
 * @author marcus
 *
 * 2018-01-14
 */
@Api(value = "会员签名", description = "会员签名移动端接口管理")
@RestController
public class MemberSignatureMobileController extends BaseController {
    
    @Autowired
    private UserRpc userRpc;
    
    @Autowired
    private MemberSignatureService memberSignatureService;
    
    @ApiOperation(value = "更新会员签名")
    @RequestMapping(value = "/member/signature/admin/v1/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateV1(@RequestBody MemberSignature body) {
        validateRequest(
                body,
                MemberSignature.APP_ID,
                MemberSignature.MEMBER_SIGNATURE,
                MemberSignature.SYSTEM_REQUEST_USER_ID
        );

        User user = userRpc.findV1(body.getSystemRequestUserId());
        //删除旧的会员签名
        memberSignatureService.deleteByMemberId(user.getObjectId(), body.getSystemRequestUserId());
        
        body.setMemberId(user.getObjectId());
        //保存新的会员签名
        Boolean result = memberSignatureService.save(body, Util.getRandomUUID(), body.getSystemRequestUserId());

        return renderJson(result);
    }

}