package com.ecommerce.auth_service.constant;

public final class ValidateConstant {
    public static final String LOGIN_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String PHONE_REGEX = "^0\\d{9,10}$";
    private ValidateConstant(){}
}
