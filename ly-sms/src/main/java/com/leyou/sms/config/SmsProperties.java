package com.leyou.sms.config;


import org.springframework.stereotype.Component;

/**
 * @author 李静
 * @date 2019/9/17 9:55
 */
public class SmsProperties {

    /**
     * #accessKeyId
     */
    String accessKeyId = "LTAI4Fe6Y92BKnNsnXHCYLrF";

    /**
     * #AccessKeySecret
     */
    String accessKeySecret = "izdNkF8zb4ZFwxKLIItrorqj4cJzcR";

    /**
     *  # 签名名称
     */
    String signName = "乐优商城";

    /**
     * # 模板名称
     */
    String verifyCodeTemplate = "SMS_174024485";

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getVerifyCodeTemplate() {
        return verifyCodeTemplate;
    }

    public void setVerifyCodeTemplate(String verifyCodeTemplate) {
        this.verifyCodeTemplate = verifyCodeTemplate;
    }
}
