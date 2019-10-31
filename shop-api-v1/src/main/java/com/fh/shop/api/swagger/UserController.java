package com.fh.shop.api.swagger;

import com.fh.shop.api.commons.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: Bryan
 * @Date: 2018/12/29 18:15
 */
@Api(description = "用户操作接口")
@Controller("user")
@RequestMapping("/user")
public class UserController {

    @ApiOperation(value = "注册", notes="注册用户")
    @ApiImplicitParam(name = "telephone", value = "用户名", paramType = "query", required = true, dataType = "Integer")
    @RequestMapping(value = "getotp", method=RequestMethod.GET)
    @ResponseBody
    public ServerResponse getOtp(@RequestParam(name = "telephone") String telphone) {
        System.out.println(telphone);
        return ServerResponse.success("注册成功" + telphone);
    }

}
