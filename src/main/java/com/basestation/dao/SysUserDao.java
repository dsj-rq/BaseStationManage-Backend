package com.basestation.dao;

import com.basestation.bean.SysPermission;
import com.basestation.bean.User;
import com.basestation.config.UserRowMaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by me on 2020/4/29.
 */
@Repository
public class SysUserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> listAllUser(){
        return jdbcTemplate.query("select a.*,b.role_id from sys_user a left join sys_user_role_relation b on a.id=b.user_id",new UserRowMaper());
    }

    public User selectByName(String username) {
        String sql = String.format("select a.*,b.role_id from sys_user a left join sys_user_role_relation b on a.id=b.user_id where account ='%s'",username);
        return jdbcTemplate.queryForObject(sql, new UserRowMaper());
    }

    /**
     * 查询用户的权限列表
     * @param userid
     * @return
     */
    public List<SysPermission> selectPermissionListByUser(int userid) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT")
                .append(" p.* ")
                .append(" FROM")
                .append(" sys_user AS u")
                .append(" LEFT JOIN sys_user_role_relation AS ur")
                .append(" ON u.id = ur.user_id")
                .append(" LEFT JOIN sys_role AS r")
                .append(" ON r.id = ur.role_id")
                .append(" LEFT JOIN sys_role_permission_relation AS rp")
                .append(" ON r.id = rp.role_id")
                .append(" LEFT JOIN sys_permission AS p")
                .append(" ON p.id = rp.permission_id")
                .append(" WHERE u.id = '")
                .append(userid)
                .append("'");
        return jdbcTemplate.query(sqlBuilder.toString(),new SysPermission());
    }


    /**
     * 查询指定路径需要的权限列表
     * @param url
     * @return
     */
    public List<SysPermission> selectPermissionListByPath(String url) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT")
                .append(" p.* ")
                .append(" FROM")
                .append(" sys_request_path AS u")
                .append(" LEFT JOIN sys_request_path_permission_relation AS ur")
                .append(" ON u.id = ur.url_id")
                .append(" LEFT JOIN sys_permission AS p")
                .append(" ON ur.permission_id = p.id")
                .append(" WHERE u.url = '")
                .append(url)
                .append("'");
        return jdbcTemplate.query(sqlBuilder.toString(),new SysPermission());
    }

    /**
     * 更新用户指定属性
     * @return
     */
    public boolean updateUserAttr(String userId,String key ,String val) {
        String sql = String.format("update sys_user set %s=? where id=?",key);
        return jdbcTemplate.update(sql,val,userId) > 0;
    }

    public boolean reSetUserType(String userId,String roleId    ){
        String deleteSql = "delete from sys_user_role_relation where user_id=?";
        jdbcTemplate.update(deleteSql,userId);
        String addSql = "insert  into sys_user_role_relation(user_id,role_id)values(?,?)";
        return jdbcTemplate.update(addSql,userId,roleId)>0;
    }

    /**
     * 删除指定用户
     * @param id
     * @return
     */
    public boolean deleteUser(String id) {
        String sql = "delete from sys_user where id=?";
        return jdbcTemplate.update(sql,id) > 0;
    }

    public boolean addUser(User user) {
        String insertSql = "insert into sys_user(account,user_name,password) values (?,?,?)";
        return jdbcTemplate.update(insertSql,user.getAccount(),user.getUsername(),user.getPassword())>0;
    }
}
