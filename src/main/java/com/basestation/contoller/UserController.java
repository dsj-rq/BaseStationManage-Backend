package com.basestation.contoller;

import com.basestation.anootation.LoginRequired;
import com.basestation.bean.CommonResponse;
import com.basestation.bean.User;
import com.basestation.common.ResultCode;
import com.basestation.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息控制器
 */
@RestController
@RequestMapping(value = "/api/v1/user" ,method={RequestMethod.GET,RequestMethod.POST})
@Api(value = "/rest", tags = "UserController", description = "用户管理接口")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getCurrentUserInfo")
    public CommonResponse getCurrentUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if(principal instanceof  User) {
            return new CommonResponse().success(principal);
        }
        return new CommonResponse().error(ResultCode.USER_NOT_LOGIN);
    }

    @RequestMapping("/list")
    public CommonResponse list(){
        return userService.listAllUser();
    }

    /**
     * 添加用户
     * @param user
     * @return
     */

    @LoginRequired
    @ApiOperation(value = "添加用户")
    @RequestMapping("/addUser")
    public  CommonResponse addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @ApiOperation(value = "删除用户")
    @RequestMapping("/deleteUser")
    public CommonResponse deleteUser(String userId){
        return userService.deleteUser(userId);
    }

    /**
     * 更新用户信息
     * @param id
     * @param key
     * @param val
     * @return
     */
    @ApiOperation(value = "更新用户信息")
    @RequestMapping("/updateUser")
    public CommonResponse updateUser(String id,String key,String val) {
        return userService.updateUser(id,key,val);
    }
}
