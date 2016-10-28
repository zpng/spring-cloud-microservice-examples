/**
 * @(#)UserService.java, 十月 28, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.simple.service.service;

import cloud.simple.service.data.User;
import cloud.simple.service.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhangpeng
 */
@Service
@Transactional
public class UserService {


    @Autowired
    private UserStorage userMapper;

    public List<User> searchAll(){
        List<User> list = userMapper.findAll();
        return list;
    }
}