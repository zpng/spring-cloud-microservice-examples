/**
 * @(#)UserController.java, 十月 28, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.simple.service.web;

import cloud.simple.service.data.User;
import cloud.simple.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangpeng
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value="/user",method= RequestMethod.GET)
    public List<User> readUserInfo(){
        List<User> ls=userService.searchAll();
        return ls;
    }
}