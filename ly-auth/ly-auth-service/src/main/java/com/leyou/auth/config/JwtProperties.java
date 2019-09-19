package com.leyou.auth.config;

import com.leyou.auth.utils.RsaUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author Administrator
 */
@Data
public class JwtProperties {

    /**
     * // 密钥
     */
    private String secret = "ly@Login(Auth}*^31)&yun6%f3q2";
    /**
     * // 公钥
     */
    private String pubKeyPath = "E:/tmp/rsa/rsa.pub";
    /**
     * // 私钥
     */
    private String priKeyPath = "E:/tmp/rsa/rsa.pri";
    /**
     * // token过期时间
     */
    private int expire = 30;
    /**
     * cookieName
     */
    private String cookieName = "LY_TOKEN";
    /**
     * // 公钥
     */
    private PublicKey publicKey;
    /**
     *  // 私钥
     */
    private PrivateKey privateKey;

    private Integer cookieMaxAge = 30;

    private static final Logger logger = LoggerFactory.getLogger(JwtProperties.class);

    /**
     *  // 对象一旦实例化后，就应该读取公钥和私钥
     * // 构造函数执行完毕后就执行
     */
    public void init(){
        try {
            //公钥和私钥不存在  要先生成
            File pubKey = new File(pubKeyPath);
            File priKey = new File(priKeyPath);
            if (!pubKey.exists() || !priKey.exists()) {
                // 生成公钥和私钥
                RsaUtils.generateKey(pubKeyPath, priKeyPath, secret);
            }
            // 获取公钥和私钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
            this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
        } catch (Exception e) {
            logger.error("初始化公钥和私钥失败！", e);
            throw new RuntimeException();
        }
    }

}
