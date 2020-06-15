package com.basestation.handler;

import com.alibaba.fastjson.JSON;
import com.basestation.bean.CommonResponse;
import com.basestation.common.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 会话信息过期策略
 * Created by me on 2020/4/19.
 */
@Component
public class SysSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    Logger logger = LoggerFactory.getLogger(SysSessionInformationExpiredStrategy.class);
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        logger.warn("session timeout！");
        CommonResponse rsp = new CommonResponse();
        HttpServletResponse httpServletResponse = sessionInformationExpiredEvent.getResponse();
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(rsp.error(ResultCode.USER_ACCOUNT_USE_BY_OTHERS)));
    }
}
