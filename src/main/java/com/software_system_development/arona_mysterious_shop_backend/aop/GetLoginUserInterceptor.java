package com.software_system_development.arona_mysterious_shop_backend.aop;

import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;
import com.software_system_development.arona_mysterious_shop_backend.service.UserService;
import com.software_system_development.arona_mysterious_shop_backend.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * 获取当前登录用户并存到 threadLocal
 *
 * @author pine
 */
@Aspect
@Component
@Order(2)
public class GetLoginUserInterceptor {

    @Resource
    private UserService userService;

    /**
     * 执行拦截
     *
     * @param point 切入点
     * @return {@link Object}
     * @throws Throwable 异常
     */
    @Around("execution(public * com.software_system_development.arona_mysterious_shop_backend.controller.*.*(..))")
    public Object doInterceptor(ProceedingJoinPoint point) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        /*
         * 获取当前登录用户并存到 threadLocal
         *  需要登录的接口在使用 ThreadLocalUtil 获取用户信息时抛出异常
         *
         */
        UserVO loginUser = null;
        try {
            loginUser = userService.getUserVO(request);
        } catch (Exception ignored) {
        }
        ThreadLocalUtil.setLoginUser(loginUser);
        try {
            return point.proceed();
        } finally {
            ThreadLocalUtil.removeAll();
        }

    }
}

