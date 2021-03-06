package com.nowui.cloud.base.role.entity;

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
 * 角色菜单
 *
 * @author marcus
 *
 * 2018-01-02
 */
@Component
@Document(indexName = "nowui", type = "role_menu_map")
@TableName(value = "role_menu_map")
public class RoleMenu extends BaseEntity {

    /**
     * 角色菜单编号
     */
    @Id
    @TableId
    @NotNull(message = "角色菜单编号不能为空")
    @Length(max = 32, message = "角色菜单编号长度超出限制")
    private String roleMenuId;
    public static final String ROLE_MENU_ID = "roleMenuId";

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
     * 角色编号
     */
    @Field
    @TableField
    @NotNull(message = "角色编号不能为空")
    @Length(max = 32, message = "角色编号长度超出限制")
    private String roleId;
    public static final String ROLE_ID = "roleId";

    /**
     * 菜单编号
     */
    @Field
    @TableField
    @NotNull(message = "菜单编号不能为空")
    @Length(max = 32, message = "菜单编号长度超出限制")
    private String menuId;
    public static final String MENU_ID = "menuId";


    public String getRoleMenuId() {
        return getString(ROLE_MENU_ID);
    }

    public void setRoleMenuId(String roleMenuId) {
        put(ROLE_MENU_ID, roleMenuId);
    }
    
    public String getAppId() {
        return getString(APP_ID);
    }

    public void setAppId(String appId) {
        put(APP_ID, appId);
    }
    
    public String getRoleId() {
        return getString(ROLE_ID);
    }

    public void setRoleId(String roleId) {
        put(ROLE_ID, roleId);
    }
    
    public String getMenuId() {
        return getString(MENU_ID);
    }

    public void setMenuId(String menuId) {
        put(MENU_ID, menuId);
    }

}