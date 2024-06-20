package org.example.service;

import org.example.bean.User;
import org.example.dao.UserDao;

public class UserService {
    private final UserDao userDao=new UserDao();

    public User login(String email, String password){
        return userDao.login(email, password);
    }


}
