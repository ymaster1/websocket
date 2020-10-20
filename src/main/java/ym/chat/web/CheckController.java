package ym.chat.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.servlet.http.HttpSession;

import ym.chat.Response;
import ym.chat.User;
import ym.chat.service.CheckService;

/**
 * @Author: ym
 * @Description: 用户验证入口
 * @Date: 2020/3/21 12:59 下午
 * @Version:
 */
@RequestMapping("check")
@RestController
public class CheckController {
    @Autowired
    private CheckService service;

    /**
     * 登陆
     * @return
     */
    @RequestMapping("/login")
    public Response login(@RequestBody User user,HttpSession session){
        return Response.success(service.login(user.getName(),user.getPassword(),session));
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping("/register")
    public Response register(@RequestBody User user, HttpSession session){
        return Response.success(service.register(user,session));
    }

    /**
     * 跳转到注册页面
     * @return
     */
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }
}
