package com.leyou.auth.service;

import com.leyou.auth.client.UserClient;
import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.pojo.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.utils.CookieUtils;
import leyou.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 李静
 * @date 2019/9/18 15:53
 */
@Slf4j
@Service
public class AuthService {

    @Autowired
    private UserClient userClient;

    public String login(String username, String password) {
        try {
            // 校验用户
            User user = userClient.queryUsernameAndPassword(username, password);
            if(user == null) {
                throw new LyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
            }
            // 生成cookie
            JwtProperties properties = new JwtProperties();
            properties.init();
            String token = JwtUtils.generateToken(new UserInfo(user.getId(), username),
                    properties.getPrivateKey(),
                    properties.getExpire());
            return token;
        }catch (Exception e) {
            log.error("【授权中心】生成token失败，用户名称：{}",username, e);
            throw new LyException(ExceptionEnum.CREATE_TOKEN_ERROR);
        }
    }

    public UserInfo verifyUser(String token, HttpServletRequest request,HttpServletResponse response) {
        try {
            // 获取公钥
            JwtProperties properties = new JwtProperties();
            properties.init();
            // 从token中解析token信息
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, properties.getPublicKey());
            // 解析成功要重新刷新token
            String newToken = JwtUtils.generateToken(userInfo, properties.getPrivateKey(), properties.getExpire());
            // 更新cookie中的token
            CookieUtils.newBuilder(response).httpOnly().request(request).build(properties.getCookieName(), newToken);
            return userInfo;
        }catch (Exception e) {
            log.error("【授权中心】验证用户信息失败", e);
            throw new LyException(ExceptionEnum.UNAUTHORIZED);
        }
    }
}
