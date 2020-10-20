package ym.chat.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import ym.chat.User;

/**
 * @Author: ym
 * @Description:
 * @Date: 2020/3/21 1:08 下午
 * @Version:
 */
public interface CheckService {
    /**
     * 注册
     * @param user
     */
    Boolean register(User user, HttpSession session);

    /**
     * 登陆
     * @param name
     * @param pwd
     */
    Boolean login(String name, String pwd, HttpSession session);

    /**
     * 退出
     */
    void logout();

    /**
     * 修改密码
     */
    void update(User user);
}
