/**
 * @(#)UserController.java, 十月 28, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.simple.web;

import cloud.simple.data.User;
import cloud.simple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangpeng
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value="/users")
    public ResponseEntity<List<User>> readUserInfo(){
        List<User> users=userService.readUserInfo();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
}