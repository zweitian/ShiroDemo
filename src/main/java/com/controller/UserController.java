package com.controller;

import com.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:ztian
 * @Description:
 * @CreateTime: 2018/3/4  0:18
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);

    //用户登录
    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());

        logger.debug("是否已经登录:{}",subject.isAuthenticated());
        try{
            // login方法会跳到我们自定义的realm中
            subject.login(token);
            request.getSession().setAttribute("user", user);
            request.setAttribute("msg","login success");
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            request.getSession().setAttribute("user", user);
            request.setAttribute("error", "用户名或密码错误！");
            return "login";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "unauthenized";
    }

    @RequestMapping("/admin")
    public String admin(HttpServletRequest request) {
        request.setAttribute("msg","admin success");
        return "success";
    }

    @RequestMapping("/student")
    public String student(HttpServletRequest request) {
        request.setAttribute("msg","student success");
        return "success";
    }

    @RequestMapping("/teacher")
    public String teacher(HttpServletRequest request) {
        request.setAttribute("msg","teacher success");
        return "success";
    }
}
