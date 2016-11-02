/**
 * @(#)UserServiceImpl.java, 十一月 02, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.thrift.server.service;

import cloud.thrift.UserDto;
import cloud.thrift.UserService;
import org.apache.thrift.TException;

/**
 * @author zhangpeng
 */
public class UserServiceImpl implements UserService.Iface{

    @Override
    public UserDto getUser() throws TException
    {
        UserDto user = new UserDto(1000,"david");
        return user;
    }

}
