package com.basestation.handler;

import com.alibaba.fastjson.JSON;
import com.basestation.bean.SysPermission;
import com.basestation.dao.SysUserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 安全元数据源
 * Created by me on 2020/4/29.
 */
@Component
public class SysFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    Logger logger = LoggerFactory.getLogger(SysFilterInvocationSecurityMetadataSource.class);
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        logger.info("鉴权url：{}",requestUrl);
        //查询具体某个接口的权限
        List<SysPermission> permissionList = sysUserDao.selectPermissionListByPath(requestUrl);
        if (permissionList == null || permissionList.size() == 0) {
            //请求路径没有配置权限，表明该请求接口可以任意访问
            return null;
        }
        String[] attributes = new String[permissionList.size()];
        for (int i = 0; i < permissionList.size(); i++) {
            attributes[i] = permissionList.get(i).getPermissionCode();
        }
        logger.info("url[{}] 已配置权限:[{}]",requestUrl, JSON.toJSONString(attributes));
        return SecurityConfig.createList(attributes);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
