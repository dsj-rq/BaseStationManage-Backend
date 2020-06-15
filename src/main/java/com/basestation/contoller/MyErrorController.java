package com.basestation.contoller;

import com.basestation.bean.CommonResponse;
import com.basestation.common.ResultCode;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by me on 2020/5/4.
 */
@RestController
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public CommonResponse error(){
        return new CommonResponse().error(ResultCode.COMMON_FAIL);
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
