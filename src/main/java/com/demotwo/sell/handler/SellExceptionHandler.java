package com.demotwo.sell.handler;

import com.demotwo.sell.exception.SellException;
import com.demotwo.sell.exception.SellVerifyException;
import com.demotwo.sell.util.ResultVOUtil;
import com.demotwo.sell.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: liudongyang
 * @Date: 2018/9/29 1:36
 * @Desc:
 */
@ControllerAdvice
public class SellExceptionHandler {

    //拦截登录异常
    //返回到登录界面，这里用重定向到百度替代
    @ExceptionHandler(SellVerifyException.class)
    public ModelAndView handlerAuthorizeException() {
        return new ModelAndView("redirect:".concat("http://www.baidu.com"));
    }

    //统一异常捕获 后端抛出异常把以前的VO视图给覆盖掉了，这里统一处理异常
    @ExceptionHandler(SellException.class)
    @ResponseBody
    public ResultVO sellExceptionHandler(SellException e) {
        return ResultVOUtil.error(e.getState(), e.getMessage());
    }
}
