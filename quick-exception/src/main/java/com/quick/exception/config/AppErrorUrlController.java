package com.quick.exception.config;

import com.quick.exception.utils.BaseResp;
import com.quick.exception.utils.ResultStatus;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/12/1
 * Time: 13:42
 * Description: 当用户输入不合法的url时，返回标准结果(json字符串，而不是页面)
 */
@RestController
public class AppErrorUrlController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public BaseResp<?> error() {
        return new BaseResp<String>(ResultStatus.error_invalid_argument,ResultStatus.error_invalid_argument.name());
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
