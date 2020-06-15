package com.basestation.service;

import com.basestation.bean.CommonResponse;
import com.basestation.bean.SysPermission;
import com.basestation.bean.User;
import com.basestation.dao.SysUserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private SysUserDao sysUserDao;
    private Logger logger = LoggerFactory.getLogger(BaseStationService.class);

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final String ERROR_INFO_STR = "用户服务异常！";
    /**
     * 获取用户列表
     * @return
     */
    public CommonResponse listAllUser(){
        try {
            return new CommonResponse().success(sysUserDao.listAllUser());
        } catch (Exception e) {
            logger.error(ERROR_INFO_STR,e);
        }
        return new CommonResponse().error(ERROR_INFO_STR);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || "".equals(username)) {
            throw new RuntimeException("用户不能为空");
        }
        //根据用户名查询用户
        User sysUser = sysUserDao.selectByName(username);
        sysUser.setPassword(passwordEncoder.encode("111111"));
        System.out.println("密码:"+sysUser.getPassword());
        if (sysUser == null) {
            throw new RuntimeException("用户不存在");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (sysUser != null) {
            //获取该用户所拥有的权限
            List<SysPermission> sysPermissions = sysUserDao.selectPermissionListByUser(sysUser.getId());
            // 声明用户授权
            sysPermissions.forEach(sysPermission -> {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission.getPermissionCode());
                grantedAuthorities.add(grantedAuthority);
            });
        }
        sysUser.setAuthorities(grantedAuthorities);
        return sysUser;

    }

    public CommonResponse deleteUser(String userId) {
        try {
            return new CommonResponse().success(sysUserDao.deleteUser(userId));
        } catch (Exception e) {
            logger.error(ERROR_INFO_STR,e);
        }
        return new CommonResponse().error(ERROR_INFO_STR);
    }

    public CommonResponse addUser(User user) {
        try {
            String psw = passwordEncoder.encode(user.getPassword());
            user.setPassword(psw);
            return new CommonResponse().success(sysUserDao.addUser(user));
        } catch (Exception e) {
            logger.error(ERROR_INFO_STR,e);
        }
        return new CommonResponse().error(ERROR_INFO_STR);
    }

    public CommonResponse updateUser(String id,String key,String val) {
        if("admin".equals(key)) {
            //更新用户类型 ，管理员或非管理员
            boolean flag = Boolean.valueOf(val);
            if(flag) {
                //将当前id更新
                return new CommonResponse().success(sysUserDao.reSetUserType(id,"1"));
            } else {
                return new CommonResponse().success(sysUserDao.reSetUserType(id,"2"));
            }
        }
        try {
            return new CommonResponse().success(sysUserDao.updateUserAttr(id,key,val));
        } catch (Exception e) {
            logger.error(ERROR_INFO_STR,e);
        }
        return new CommonResponse().error(ERROR_INFO_STR);
    }
}
