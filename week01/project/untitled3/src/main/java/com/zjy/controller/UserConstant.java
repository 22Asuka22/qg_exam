package com.zjy.controller;

public class UserConstant {
        private UserConstant() {}

        public static final String STUDENT_ID_REGEX = "3[1-2]25\\d{6}";
        public static final String ADMIN_ID_REGEX = "0025\\d{6}";

        public static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-zA-Z])\\w{6,12}$";
        public static final String PHONE_REGEX = "1[3-9]\\d{9}";

        public static final String STUDENT_ID_ERROR_MSG = "学号必须是9位纯数字！";
        public static final String PASSWORD_ERROR_MSG = "密码需6-12位，包含字母+数字！";
        public static final String LOGIN_SUCCESS_MSG = "登录成功！";
        public static final String LOGIN_FAIL_MSG = "账号或密码错误！";
        public static final String WRONG_INPUT_MSG = "输入不合法！";
        public static final String OVER_INPUT_MSG = "请输入正确选项！";
        public static final String DB_WRONG_MSG = "数据库操作错误";
        public static final String REPEAT_WRONG_MSG = "两次输入的密码不同";

        // 4. 默认值（硬编码抽离）
        public static final String DEFAULT_PASSWORD = "123456a";


}
