package com.heyqing.cvarst_back.dao.pojo;

import lombok.Data;

import java.util.List;

/**
 * ClassName:SysUser
 * Package:com.heyqing.cvarst_back.dao.pojo
 * Description:
 *
 * @Date:2023/11/10
 * @Author:Heyqing
 */
@Data
public class SysUser {

    private Long id;

    private String openid;

    private String token;

    private String code;

    private String encryptedData;

    private String  iv;

    private String rawData;

    private String  signature;

    private Long createDate;

    private Long lastLogin;


}
