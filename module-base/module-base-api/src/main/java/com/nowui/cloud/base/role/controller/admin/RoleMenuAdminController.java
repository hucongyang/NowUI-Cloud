package com.nowui.cloud.base.role.controller.admin;
import com.nowui.cloud.controller.BaseController;
import com.nowui.cloud.util.Util;
import com.nowui.cloud.base.role.entity.RoleMenu;
import com.nowui.cloud.base.role.service.RoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色菜单管理端控制器
 *
 * @author marcus
 *
 * 2018-01-02
 */
@Api(value = "角色菜单", description = "角色菜单管理端接口管理")
@RestController
public class RoleMenuAdminController extends BaseController {

    @Autowired
    private RoleMenuService roleMenuService;

    @ApiOperation(value = "角色菜单列表")
    @RequestMapping(value = "/role/menu/admin/v1/list", method = {RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> listV1(@RequestBody RoleMenu body) {
        validateRequest(
                body,
                RoleMenu.APP_ID,
                RoleMenu.ROLE_ID,
                RoleMenu.MENU_ID,
                RoleMenu.PAGE_INDEX,
                RoleMenu.PAGE_SIZE
        );

        Integer resultTotal = roleMenuService.countForAdmin(body.getAppId() , body.getRoleId(), body.getMenuId());
        List<RoleMenu> resultList = roleMenuService.listForAdmin(body.getAppId(), body.getRoleId(), body.getMenuId(), body.getPageIndex(), body.getPageSize());

        validateResponse(
                RoleMenu.ROLE_MENU_ID,
                RoleMenu.ROLE_ID,
                RoleMenu.MENU_ID
        );

        return renderJson(resultTotal, resultList);
    }

    @ApiOperation(value = "角色菜单信息")
    @RequestMapping(value = "/role/menu/admin/v1/find", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> findV1(@RequestBody RoleMenu body) {
        validateRequest(
                body,
                RoleMenu.APP_ID,
                RoleMenu.ROLE_MENU_ID
        );

        RoleMenu result = roleMenuService.find(body.getRoleMenuId());

        validateResponse(
                RoleMenu.ROLE_MENU_ID,
                RoleMenu.ROLE_ID,
                RoleMenu.MENU_ID
        );

        return renderJson(result);
    }

    @ApiOperation(value = "新增角色菜单")
    @RequestMapping(value = "/role/menu/admin/v1/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> saveV1(@RequestBody RoleMenu body) {
        validateRequest(
                body,
                RoleMenu.APP_ID,
                RoleMenu.ROLE_ID,
                RoleMenu.MENU_ID
        );

        Boolean result = roleMenuService.save(body, Util.getRandomUUID(), body.getSystemRequestUserId());

        return renderJson(result);
    }

    @ApiOperation(value = "修改角色菜单")
    @RequestMapping(value = "/role/menu/admin/v1/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateV1(@RequestBody RoleMenu body) {
        validateRequest(
                body,
                RoleMenu.ROLE_MENU_ID,
                RoleMenu.APP_ID,
                RoleMenu.ROLE_ID,
                RoleMenu.MENU_ID,
                RoleMenu.SYSTEM_VERSION
        );

        Boolean result = roleMenuService.update(body, body.getRoleMenuId(), body.getSystemRequestUserId(), body.getSystemVersion());

        return renderJson(result);
    }

    @ApiOperation(value = "删除角色菜单")
    @RequestMapping(value = "/role/menu/admin/v1/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteV1(@RequestBody RoleMenu body) {
        validateRequest(
                body,
                RoleMenu.ROLE_MENU_ID,
                RoleMenu.APP_ID,
                RoleMenu.SYSTEM_VERSION
        );

        Boolean result = roleMenuService.delete(body.getRoleMenuId(), body.getSystemRequestUserId(), body.getSystemVersion());

        return renderJson(result);
    }

}