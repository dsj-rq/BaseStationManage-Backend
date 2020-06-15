package com.basestation.config;

import com.basestation.bean.User;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by me on 2020/4/30.
 */
public class UserRowMaper implements RowMapper<com.basestation.bean.User>, Serializable {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("user_name"));
        user.setAccount(resultSet.getString("account"));
        user.setPassword(resultSet.getString("password"));
        user.setEnabled(resultSet.getBoolean("enabled"));
        user.setAccountNonExpired(resultSet.getBoolean("account_non_expired"));
        user.setAccountNonLocked(resultSet.getBoolean("account_not_locked"));
        user.setCredentialsNonExpired(resultSet.getBoolean("credentials_not_expired"));
        user.setRoleId(resultSet.getInt("role_id"));
        return user;
    }
}
