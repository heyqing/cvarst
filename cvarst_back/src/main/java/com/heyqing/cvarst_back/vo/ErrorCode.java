package com.heyqing.cvarst_back.vo;

/**
 * ClassName:ErrorCode
 * Package:com.heyqing.cvarst_back.vo
 * Description:
 *
 * @Date:2023/11/10
 * @Author:Heyqing
 */
public enum ErrorCode {

    PARAMS_ERROR(10001,"参数有误"),
    ACCOUNT_PWD_NOT_EXIST(10002,"用户名或密码不存在"),
    TOKEN_ERROR(10003,"token不合法"),
    ACCOUNT_EXIST(10004,"账户已经存在"),
    NO_PERMISSION(70001,"无访问权限"),
    SESSION_TIME_OUT(90001,"会话超时"),
    NO_LOGIN(90002,"未登录"),
    UPLOAD_FAIL(20001,"上传失败"),
    SYSTEM_FAIL(-999,"系统错误");
    private int code;
    private String msg;

    ErrorCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
