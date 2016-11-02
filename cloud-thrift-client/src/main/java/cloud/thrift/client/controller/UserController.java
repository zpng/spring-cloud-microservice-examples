/**
 * @(#)UserController.java, 十一月 02, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.thrift.client.controller;

import cloud.thrift.UserDto;
import cloud.thrift.UserService;
import cloud.thrift.client.provider.UserServiceProvider;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhangpeng
 */
@Controller
public class UserController {


    @Autowired
    UserServiceProvider userServiceProvider;

    @ResponseBody
    @RequestMapping(value = "/hello")
    String hello() throws TException {
        UserService.Client svr=userServiceProvider.getBalanceUserService();
        UserDto userDto= svr.getUser();
        return "hi "+userDto.getUsername();
    }
}