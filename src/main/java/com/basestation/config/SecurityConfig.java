package com.basestation.config;

import com.basestation.handler.*;
import com.basestation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService authService;
    //登录成功处理逻辑
    @Autowired
    SysAuthenticationSuccessHandler authenticationSuccessHandler;
    //登录失败处理逻辑
    @Autowired
    SysAuthenticationFailureHandler authenticationFailureHandler;
    //登出成功处理逻辑
    @Autowired
    SysLogoutSuccessHandler logoutSuccessHandler;
    //会话失效(账号被挤下线)处理逻辑
    @Autowired
    SysSessionInformationExpiredStrategy sessionInformationExpiredStrategy;
    //权限拒绝处理逻辑
    @Autowired
    SysAccessDeniedHandler accessDeniedHandler;
    //匿名用户访问无权限资源时的异常
    @Autowired
    SysAuthenticationEntryPoint authenticationEntryPoint;
    //访问决策管理器
    @Autowired
    SysAccessDecisionManager accessDecisionManager;
    //实现权限拦截
    @Autowired
    SysFilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    private SysAbstractSecurityInterceptor securityInterceptor;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder：Spring Security 提供的加密工具，可快速实现加密加盐
        return new BCryptPasswordEncoder();
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启登录配置
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(accessDecisionManager);//决策管理器
                        o.setSecurityMetadataSource(securityMetadataSource);//安全元数据源
                        return o;
                    }
                })
                .and().logout().
                permitAll().//允许所有用户
                logoutSuccessHandler(logoutSuccessHandler).//登出成功处理逻辑
                deleteCookies("JSESSIONID").//登出之后删除cookie
                //登入
                and().formLogin()
                .permitAll()//允许所有用户
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler)//登录成功处理逻辑
                .failureHandler(authenticationFailureHandler)//登录失败处理逻辑
                //异常处理(权限拒绝、登录失效等)
                //.and()
                //.exceptionHandling().
                //accessDeniedHandler(accessDeniedHandler).//权限拒绝处理逻辑
                //authenticationEntryPoint(authenticationEntryPoint).//匿名用户访问无权限资源时的异常处理
                //会话管理
                .and().sessionManagement().
                maximumSessions(1).//同一账号同时登录最大用户数
                expiredSessionStrategy(sessionInformationExpiredStrategy);//会话失效(账号被挤下线)处理逻辑
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略url - 会直接过滤该url - 将不会经过Spring Security过滤器链
        web.ignoring().antMatchers("/getUserInfo");
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/static/**","/static/**/**");
    }
}
