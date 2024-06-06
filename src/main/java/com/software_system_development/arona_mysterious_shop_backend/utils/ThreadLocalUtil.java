package com.software_system_development.arona_mysterious_shop_backend.utils;

import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import com.software_system_development.arona_mysterious_shop_backend.model.vo.UserVO;

public final class ThreadLocalUtil {
    private static final ThreadLocal<UserVO> LOGIN_USER = ThreadLocal.withInitial(UserVO::new);

    public static UserVO getLoginUser() {
        UserVO user = LOGIN_USER.get();
        if (user == null || user.getUserId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return user;
    }

    public static void setLoginUser(UserVO user) {
        LOGIN_USER.set(user);
    }

    public static void removeLoginUser() {
        LOGIN_USER.remove();
    }

    /**
     * 清空当前线程的所有 ThreadLocal 变量
     */
    public static void removeAll() {
        removeLoginUser();
    }
}