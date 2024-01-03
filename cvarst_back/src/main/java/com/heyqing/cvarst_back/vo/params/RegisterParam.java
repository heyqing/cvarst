package com.heyqing.cvarst_back.vo.params;

import lombok.Data;

/**
 * ClassName:RegisterParam
 * Package:com.heyqing.cvarst_back.vo.params
 * Description:
 *
 * @Date:2023/11/11
 * @Author:Heyqing
 */
@Data
public class RegisterParam {

    private String code;

    private String encryptedData;

    private String iv;

    private String rawData;

    private String signature;
}
