package com.itheima.tools;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.itheima.domain.UserDTO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.itheima.tools.Prefix.LOGIN_USER;

public class LoginInterceptor implements HandlerInterceptor {
    StringRedisTemplate stringRedisTemplate;

    public LoginInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取token
        String token = request.getHeader("authorization");
        System.out.println(token);
        System.out.println("到这了");
        if (StrUtil.isBlank(token)) {
            response.setStatus(401);
            return false;
        }
        //查询
        Map<Object, Object> usermap = stringRedisTemplate.opsForHash().entries(LOGIN_USER + token);
        //判断是否存在
        if (usermap.isEmpty()) {
            response.setStatus(200);
            response.setHeader("Content-Type","application/json;charset=UTF-8");
            response.getWriter().print("msg: '请登录'");
            return false;
        }
        //map转换为对象
        UserDTO loginUser = BeanUtil.fillBeanWithMap(usermap, new UserDTO(), false);
        //存到ThreadLocal中
        UserOps.setUser(loginUser);
       //刷新token有效时间
        stringRedisTemplate.expire(LOGIN_USER + token,30, TimeUnit.MINUTES);
        return true;
    }
}
