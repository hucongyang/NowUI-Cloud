package com.nowui.cloud.member.member.controller.mobile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nowui.cloud.base.file.entity.File;
import com.nowui.cloud.base.file.rpc.FileRpc;
import com.nowui.cloud.base.user.entity.User;
import com.nowui.cloud.base.user.entity.UserAvatar;
import com.nowui.cloud.base.user.entity.UserNickName;
import com.nowui.cloud.base.user.rpc.UserRpc;
import com.nowui.cloud.controller.BaseController;
import com.nowui.cloud.member.member.entity.MemberDialogue;
import com.nowui.cloud.member.member.entity.MemberDialogueRecord;
import com.nowui.cloud.member.member.service.MemberDialogueRecordService;
import com.nowui.cloud.member.member.service.MemberDialogueService;
import com.nowui.cloud.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 会员对话移动端控制器
 *
 * @author marcus
 *
 * 2018-01-08
 */
@Api(value = "会员对话", description = "会员对话移动端接口管理")
@RestController
public class MemberDialogueMobileController extends BaseController {
    
    @Autowired
    private MemberDialogueService memberDialogueService;
    
    @Autowired
    private MemberDialogueRecordService memberDialogueRecordService;
    
    @Autowired
    private UserRpc userRpc;
    
    @Autowired
    private FileRpc fileRpc;
    
    @ApiOperation(value = "会员对话查找")
    @RequestMapping(value = "/member/dialogue/mobile/v1/find", method = {RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> findV1(@RequestBody MemberDialogue body) {
        validateRequest(
                body,
                MemberDialogue.APP_ID,
                MemberDialogue.RESPOND_USER_ID,
                MemberDialogue.PAGE_INDEX,
                MemberDialogue.PAGE_SIZE
        );
        
        User initiateUser = userRpc.findV1(body.getSystemCreateUserId());
        
        User respondUser = userRpc.findV1(body.getRespondUserId());
        
        MemberDialogue memberDialogue = memberDialogueService.findByInitiateUserIdAndRespondUserId(body.getSystemRequestUserId(), body.getRespondUserId());
        
        if (memberDialogue == null) {
            //创建会员对话
            memberDialogue = new MemberDialogue();
            memberDialogue.setAppId(body.getAppId());
            memberDialogue.setInitiateUserId(body.getSystemRequestUserId());
            memberDialogue.setInitiateMemberId(initiateUser.getObjectId());
            memberDialogue.setRespondUserId(body.getRespondUserId());
            memberDialogue.setRespondMemberId(respondUser.getObjectId());
            memberDialogueService.save(memberDialogue, Util.getRandomUUID(), body.getSystemCreateUserId());
            
            memberDialogue.put(MemberDialogue.MEMBER_DIALOGUE_RECORD_LIST, new ArrayList<>());
            memberDialogue.put(MemberDialogue.MEMBER_DIALOGUE_RECORD_COUNT, 0);
        } else {
            Integer memberDialogueRecordCount = memberDialogueRecordService.countByMemberDialogueIdForMobile(memberDialogue.getMemberDialogueId());
            List<MemberDialogueRecord> memberDialogueRecordList = memberDialogueRecordService.listByMemberDialogueIdForMobile(memberDialogue.getMemberDialogueId(), body.getPageIndex(), body.getPageSize());
        
            UserNickName initiateUserNickNameBean = (UserNickName) initiateUser.get(User.USER_NICK_NAME);
            String initiateUserNickName = "";
            if (!Util.isNullOrEmpty(initiateUserNickNameBean)) {
                initiateUserNickName = initiateUserNickNameBean.getUserNickName();
            }
            UserAvatar initiateUserAvatarBean = (UserAvatar) initiateUser.get(User.USER_AVATAR);
            String initiateUserAvatar = "";
            if (!Util.isNullOrEmpty(initiateUserAvatarBean)) {
                File initiateUserAvatarfile = fileRpc.findV1(initiateUserAvatarBean.getUserAvatar());
                if (!Util.isNullOrEmpty(initiateUserAvatarfile)) {
                    initiateUserAvatar = initiateUserAvatarfile.getFilePath();
                }
            }
            
            UserNickName respondNickNameBean = (UserNickName) respondUser.get(User.USER_NICK_NAME);
            String respondUserNickName = "";
            if (!Util.isNullOrEmpty(respondNickNameBean)) {
                respondUserNickName = respondNickNameBean.getUserNickName();
            }
            UserAvatar respondUserAvatarBean = (UserAvatar) respondUser.get(User.USER_AVATAR);
            String respondUserAvatar = "";
            if (!Util.isNullOrEmpty(respondUserAvatarBean)) {
                File respondUserAvatarfile = fileRpc.findV1(respondUserAvatarBean.getUserAvatar());
                if (!Util.isNullOrEmpty(respondUserAvatarfile)) {
                    respondUserAvatar = respondUserAvatarfile.getFilePath();
                }
            }
            
            for (MemberDialogueRecord memberDialogueRecord : memberDialogueRecordList) {
                memberDialogueRecord.keep(
                        MemberDialogueRecord.MEMBER_DIALOGUE_RECORD_ID, 
                        MemberDialogueRecord.USER_ID, 
                        MemberDialogueRecord.MEMBER_DIALOGUE_CONTENT,
                        MemberDialogueRecord.SYSTEM_CREATE_TIME
                );
                
                if (memberDialogueRecord.getUserId().equals(initiateUser.getUserId())) {
                    memberDialogueRecord.put(UserNickName.USER_NICK_NAME, initiateUserNickName);
                    memberDialogueRecord.put(UserAvatar.USER_AVATAR, initiateUserAvatar);
                } else if (memberDialogueRecord.getUserId().equals(respondUser.getUserId())) {
                    memberDialogueRecord.put(UserNickName.USER_NICK_NAME, respondUserNickName);
                    memberDialogueRecord.put(UserAvatar.USER_AVATAR, respondUserAvatar);
                }
            }
            
            memberDialogue.put(MemberDialogue.MEMBER_DIALOGUE_RECORD_LIST, memberDialogueRecordList);
            memberDialogue.put(MemberDialogue.MEMBER_DIALOGUE_RECORD_COUNT, memberDialogueRecordCount);
        }
        
        validateResponse(
                MemberDialogue.MEMBER_DIALOGUE_ID,
                MemberDialogue.MEMBER_DIALOGUE_RECORD_LIST,
                MemberDialogue.MEMBER_DIALOGUE_RECORD_COUNT
        );

        return renderJson(memberDialogue);
    }
    
    @ApiOperation(value = "会员对话记录列表")
    @RequestMapping(value = "/member/dialogue/record/mobile/v1/list", method = {RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> recordListV1(@RequestBody MemberDialogue body) {
        validateRequest(
                body,
                MemberDialogue.MEMBER_DIALOGUE_ID,
                MemberDialogue.PAGE_INDEX,
                MemberDialogue.PAGE_SIZE,
                MemberDialogue.SYSTEM_CREATE_TIME
        );
        MemberDialogue memberDialogue = memberDialogueService.find(body.getMemberDialogueId());
        
        User initiateUser = userRpc.findV1(memberDialogue.getInitiateUserId());
        
        User respondUser = userRpc.findV1(memberDialogue.getRespondUserId());
        
        Integer memberDialogueRecordCount = memberDialogueRecordService.countByMemberDialogueIdForMobile(memberDialogue.getMemberDialogueId());

        List<MemberDialogueRecord> memberDialogueRecordList = memberDialogueRecordService.listByMemberDialogueIdForMobile(memberDialogue.getMemberDialogueId(), body.getSystemCreateTime(), body.getPageIndex(), body.getPageSize());
        
        UserNickName initiateUserNickNameBean = (UserNickName) initiateUser.get(User.USER_NICK_NAME);
        String initiateUserNickName = "";
        if (!Util.isNullOrEmpty(initiateUserNickNameBean)) {
            initiateUserNickName = initiateUserNickNameBean.getUserNickName();
        }
        UserAvatar initiateUserAvatarBean = (UserAvatar) initiateUser.get(User.USER_AVATAR);
        String initiateUserAvatar = "";
        if (!Util.isNullOrEmpty(initiateUserAvatarBean)) {
            File initiateUserAvatarfile = fileRpc.findV1(initiateUserAvatarBean.getUserAvatar());
            if (!Util.isNullOrEmpty(initiateUserAvatarfile)) {
                initiateUserAvatar = initiateUserAvatarfile.getFilePath();
            }
        }
        
        UserNickName respondNickNameBean = (UserNickName) respondUser.get(User.USER_NICK_NAME);
        String respondUserNickName = "";
        if (!Util.isNullOrEmpty(respondNickNameBean)) {
            respondUserNickName = respondNickNameBean.getUserNickName();
        }
        UserAvatar respondUserAvatarBean = (UserAvatar) respondUser.get(User.USER_AVATAR);
        String respondUserAvatar = "";
        if (!Util.isNullOrEmpty(respondUserAvatarBean)) {
            File respondUserAvatarfile = fileRpc.findV1(respondUserAvatarBean.getUserAvatar());
            if (!Util.isNullOrEmpty(respondUserAvatarfile)) {
                respondUserAvatar = respondUserAvatarfile.getFilePath();
            }
        }
        
        for (MemberDialogueRecord memberDialogueRecord : memberDialogueRecordList) {
            memberDialogueRecord.keep(
                    MemberDialogueRecord.MEMBER_DIALOGUE_RECORD_ID, 
                    MemberDialogueRecord.USER_ID, 
                    MemberDialogueRecord.MEMBER_DIALOGUE_CONTENT,
                    MemberDialogueRecord.SYSTEM_CREATE_TIME
            );
            
            if (memberDialogueRecord.getUserId().equals(initiateUser.getUserId())) {
                memberDialogueRecord.put(UserNickName.USER_NICK_NAME, initiateUserNickName);
                memberDialogueRecord.put(UserAvatar.USER_AVATAR, initiateUserAvatar);
            } else if (memberDialogueRecord.getUserId().equals(respondUser.getUserId())) {
                memberDialogueRecord.put(UserNickName.USER_NICK_NAME, respondUserNickName);
                memberDialogueRecord.put(UserAvatar.USER_AVATAR, respondUserAvatar);
            }
        }
        
        validateResponse(
                MemberDialogueRecord.MEMBER_DIALOGUE_RECORD_ID, 
                MemberDialogueRecord.USER_ID, 
                MemberDialogueRecord.MEMBER_DIALOGUE_CONTENT,
                MemberDialogueRecord.SYSTEM_CREATE_TIME,
                UserNickName.USER_NICK_NAME,
                UserAvatar.USER_AVATAR
        );

        return renderJson(memberDialogueRecordCount, memberDialogueRecordList);
    }
    
    @ApiOperation(value = "新增会员对话记录")
    @RequestMapping(value = "/member/dialogue/record/admin/v1/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> saveV1(@RequestBody MemberDialogueRecord body) {
        validateRequest(
                body,
                MemberDialogueRecord.APP_ID,
                MemberDialogueRecord.MEMBER_DIALOGUE_ID,
                MemberDialogueRecord.MEMBER_DIALOGUE_CONTENT,
                MemberDialogueRecord.SYSTEM_REQUEST_USER_ID
        );

        User user = userRpc.findV1(body.getSystemRequestUserId());
        
        body.setMemberId(user.getObjectId());
        body.setUserId(user.getUserId());
        
        Boolean result = memberDialogueRecordService.save(body, Util.getRandomUUID(), body.getSystemRequestUserId());

        return renderJson(result);
    }


}