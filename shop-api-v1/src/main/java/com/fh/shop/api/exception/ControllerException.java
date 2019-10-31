package com.fh.shop.api.exception;

import com.fh.shop.api.commons.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerException {

    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public ServerResponse handException(GlobalException e){
        return ServerResponse.error(e.getResponseEnum());
    }

}
