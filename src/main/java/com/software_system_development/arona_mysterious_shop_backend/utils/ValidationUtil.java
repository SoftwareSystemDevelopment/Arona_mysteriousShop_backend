package com.software_system_development.arona_mysterious_shop_backend.utils;

import com.software_system_development.arona_mysterious_shop_backend.common.ErrorCode;
import com.software_system_development.arona_mysterious_shop_backend.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {

    private static final String VALID_USER_ACCOUNT_PATTERN = "^[a-zA-Z0-9_-]{4,20}$";
    private static final String VALID_USER_PASSWORD_PATTERN = "^[a-zA-Z0-9_-]{8,20}$";

    public static void validateUserAccount(String userAccount) {
        if (StringUtils.isBlank(userAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号为空");
        }
        if (!isValidUserAccount(userAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号格式不正确");
        }
    }

    public static boolean isValidUserAccount(String userAccount) {
        if (StringUtils.isBlank(userAccount)) {
            return false;
        }
        Pattern pattern = Pattern.compile(VALID_USER_ACCOUNT_PATTERN);
        Matcher matcher = pattern.matcher(userAccount);
        return matcher.matches();
    }

    public static void validateUserPassword(String userPassword) {
        if (StringUtils.isBlank(userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码为空");
        }
        if (!isValidUserPassword(userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码格式不正确");
        }
    }

    public static boolean isValidUserPassword(String userPassword) {
        if (StringUtils.isBlank(userPassword)) {
            return false;
        }
        Pattern pattern = Pattern.compile(VALID_USER_PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(userPassword);
        return matcher.matches();
    }

    public static void validateUserId(Integer userId) {
        if (userId == null || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户ID不合法");
        }
    }

    public static void validateUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名为空");
        }
        if (userName.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名长度过短");
        }
    }

    public static void validateUserRole(String userRole) {
        if (StringUtils.isBlank(userRole)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户角色为空");
        }
        // 检查用户角色是否合法
        if (!UserRoleEnum.isValidUserRole(userRole)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户角色不合法");
        }
    }
}
