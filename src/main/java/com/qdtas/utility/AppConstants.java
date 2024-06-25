package com.qdtas.utility;


public class AppConstants {

    public static final String[] PUBLIC_URLS = {
            "/user/login",
            "user/resetPassword",
            "user/changeTempPassword",
            "/user/verify/**",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    public static final String TOKEN_HEADER="Jwt";


//    public static final String BASE_URL="http://localhost:8181";
    public static final String BASE_URL="https://qdtashrm-backend.onrender.com";

    public static final String[] ADMIN_URLS = {
            "/leave/approve/**",
            "/leave/reject/**",
            "/dept/add",
            "/dept/update/**",
            "/dept/delete/**",
            "/user/add"
    };

}
