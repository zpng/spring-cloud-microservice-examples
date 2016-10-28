/**
 * @(#)UserDao.java, 十月 28, 2016.
 * <p>
 * Copyright 2016 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cloud.simple.service.storage;

import cloud.simple.service.data.User;

import java.util.List;

/**
 * @author zhangpeng
 */
public interface UserStorage {

    List<User> findAll();
}
