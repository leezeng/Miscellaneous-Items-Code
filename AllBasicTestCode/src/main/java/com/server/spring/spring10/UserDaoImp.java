package com.server.spring.spring10;

import org.springframework.stereotype.Component;

/**
 * @author CYX
 * @create 2018-05-03-22:01
 */
@Component("userDaoImpl")
public class UserDaoImp implements UserDao {

    @Override
    public int addUser() {
        System.out.println("add user ......");
        return 6666;
    }

    @Override
    public void updateUser() {
        System.out.println("update user ......");
    }

    @Override
    public void deleteUser() {
        System.out.println("delete user ......");
    }

    @Override
    public void findUser() {
        System.out.println("find user ......");
    }
}
