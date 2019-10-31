package com.fh.admin.exception;

import com.fh.admin.commons.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerException {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ServerResponse exception(Exception e){
        e.printStackTrace();
        return ServerResponse.error();
    }

}
