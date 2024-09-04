package com.example.backend.utils.general;

import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class Constants {
    public static final String LOCALHOST = "http://localhost:3000";
    public static final String DEPLOY_STORE = "https://oranger.store/";
    public static final String BEARER = "Bearer ";
    public static final String PATH = "com.example.backend.web.";
    public static final String LOCAL_TIME_DATE = "LocalTimeDate";
    public static final String LOCAL_TIME_DATE_SCALAR = "LocalTimeDate scalar";
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public static final String UTF_8 = "UTF-8";
    public static final String EMAIL_KEY = "email";
    public static final String EMPTY_LINE = "";
    public static final String COOK = "; Path=/; HttpOnly; SameSite=None; Secure";
    public static final String PASSWORD = "password";
    public static final String JWT = "jwt";
    public static final String TYPE = "type";
    public static final String ROLES = "roles";
    public static final String BEARER_AUTHENTICATION = "Bearer Authentication";
    public static final String EMPTY_FIELD = "Поле не повинно бути порожнім";
    public static final Date DATE_TIME_MILLIS = new Date(System.currentTimeMillis());
    public static final String[] PERMIT_ALL = {
            "/graphiql"
    };
    public static final String[] PERMIT_ADMIN = {
            "/api/admin/**"
    };
    public static final String[] PERMIT_GET_AUTH = {
            "/api/accouth"
    };
    public static final String PASSWORD_FORMAT_MESSAGE = "password має відповідати вказаному формату";
    public static final String DELIVERY_MESSAGE = "Доставка через: ";
    public static final String BEARER_JWT_TEST = "Bearer some-jwt-token";
}