package com.lancabbage.lancodeapi.config;

import com.lancabbage.lancodeapi.bean.vo.base.BaseResponse;
import com.lancabbage.lancodeapi.enums.ResponseStatusCode;
import com.lancabbage.lancodeapi.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author tony
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public BaseResponse<?> javaExceptionHandler(Exception e) {
        log.error("Exception: ", e);
        if (e.getMessage() == null) {
            return BaseResponse.errorInstance("NullPointerException");
        }
        if (e.getMessage().contains("mysql")) {
            log.error("全局异常来作妖了: " + e);
            return BaseResponse.errorInstance("数据库开小差啦，请稍后再试");
        }
        return BaseResponse.errorInstance(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public BaseResponse<?> javaExceptionHandler(BusinessException e) {
        log.error("全局异常来作妖了: " + e);
        return BaseResponse.errorInstance(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    private BaseResponse<?> methodArgumentNotValidExceptionHandler(Exception ex) {
        //处理Validation异常
        StringBuilder errorMsg = new StringBuilder();
        MethodArgumentNotValidException c = (MethodArgumentNotValidException) ex;
        List<ObjectError> errors = c.getBindingResult().getAllErrors();
        for (ObjectError error : errors) {
            errorMsg.append(error.getDefaultMessage()).append(";");
        }
        log.error("全局异常来作妖了: " + ex);
        return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), errorMsg.toString());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    private BaseResponse<?> assertValidExceptionHandler(Exception ex) {
        //处理断言异常
        IllegalArgumentException c = (IllegalArgumentException) ex;
        log.error("全局异常来作妖了: " + ex);
        return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), c.getMessage());
    }
}
