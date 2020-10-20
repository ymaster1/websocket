package ym.chat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import ym.chat.User;
import ym.chat.exception.MyError;
import ym.chat.exception.MyException;
import ym.chat.mapper.CheckMapper;
import ym.chat.service.CheckService;
import ym.chat.util.MD5;

/**
 * @Author: ym
 * @Description:
 * @Date: 2020/3/21 1:08 下午
 * @Version:
 */
@Service
@Slf4j
public class CheckServiceImpl implements CheckService {
    @Autowired
    private CheckMapper mapper;

    @Override
    public Boolean register(User user, HttpSession session) {
        if ("" != user.getName() && user.getName() != null) {
            //看是否有重复的用户名
            User u = mapper.queryByName(user.getName());
            if (u == null) {
                // 把密码转为md5
                String pwd = MD5.md5(user.getPassword());
                user.setPassword(pwd);
                mapper.insertUser(user);
                return true;
            } else {
                throw new MyException(MyError.BODY_NOT_MATCH,"用户已存在");
            }
        }
        throw new MyException(MyError.SIGNATURE_NOT_MATCH,"用户名不能为空");
    }

    @Override
    public Boolean login(String name, String pwd, HttpSession session) {
        // 把密码转为md5
        String password = MD5.md5(pwd);
        User user = mapper.queryByNameAndpwd(name, password);
        if (user != null) {
            return true;
        }
        throw new MyException(MyError.SIGNATURE_NOT_MATCH,"用户名或密码错误");
    }

    @Override
    public void logout() {

    }

    @Override
    public void update(User user) {

    }
}
