/**
 * @(#)UserController.java, 十月 29, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.simple.serviceB.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangpeng
 */
@RestController
public class UserController {


    @RequestMapping(value="/user",method= RequestMethod.GET)
    public String readUserInfo(){
        return "Result from simpleserviceB";
    }
}