package com.basestation.bean;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by me on 2020/4/29.
 */
@Table("sys_permission")
public class SysPermission implements RowMapper, Serializable {

    private String id;

    @Column("permission_code")
    private String permissionCode;

    @Column("permission_name")
    private String permissionName;

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        SysPermission permission = new SysPermission();
        permission.setId(resultSet.getString("id"));
        permission.setPermissionCode(resultSet.getString("permission_code"));
        permission.setPermissionName(resultSet.getString("permission_name"));
        return permission;
    }
}
