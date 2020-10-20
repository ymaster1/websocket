package ym.chat.util;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: ym
 * @Description: 定义一个登录拦截器，拦截需要登录的操作，若未登录则重定向至登录界面
 * @Date: 2020/3/21 1:43 下午
 * @Version:
 */
public class LoginHandler implements HandlerInterceptor {
    /**
     * 在请求处理之前进行调用（Controller方法调用之前），可用于验证用户是否登录等。。
     *
     * @param request
     * @param response
     * @param handler
     * @return 只能返回true才能通过拦截, 否则表示取消当前请求
     * @throws Exception
     */
    //@Override
    //public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
    //        Object handler) throws Exception {
    //    //获取请求域中的session
    //    HttpSession session = request.getSession();
    //    if (session.getAttribute("userId") == null){
    //        session.setAttribute("error","请先登录！");
    //        response.sendRedirect(request.getContextPath()+"/login.html");
    //        return false;
    //    }else {
    //        session.setAttribute("userId", session.getAttribute("userId"));
    //        return true;
    //    }
    //}
}
