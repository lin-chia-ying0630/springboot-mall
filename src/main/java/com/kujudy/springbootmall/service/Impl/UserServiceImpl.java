package com.kujudy.springbootmall.service.Impl;

import com.kujudy.springbootmall.dao.UserDao;
import com.kujudy.springbootmall.dto.UserLoginRequest;
import com.kujudy.springbootmall.dto.UserRegisterRequest;
import com.kujudy.springbootmall.model.User;
import com.kujudy.springbootmall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        //查詢email是否存在
        User user=userDao.getUserByEmail(userRegisterRequest.getEmail());
        if(user!=null){
            log.warn("該EMAIL被{}使用{}",userRegisterRequest.getEmail(),"  ");
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //Md5
        String hashPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashPassword);
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user=userDao.getUserByEmail(userLoginRequest.getEmail());
        //檢查USER是否存在
        if(user==null){
            log.warn("該EMAIL被{} 尚未註冊{}",userLoginRequest.getEmail(),"   ");
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //比較密碼
        String hsahPassword =DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());
        if (user.getPassword().equals(hsahPassword)) {
            return user;
        }else{
            log.warn("該EMAIL{}不正確",userLoginRequest.getEmail());
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
