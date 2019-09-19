package com.leyou.user.service;

import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.utils.CodecUtils;
import com.leyou.utils.NumberUtils;
import leyou.user.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate template;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 保存至redis中手机验证码前缀
     */
    private static final String KEY_PREFIX = "user:verify:phone:";

    /**
     * 校验用户数据
     * @param data
     * @param type
     * @return
     */
    public Boolean checkData(String data, Integer type) {
        User user = new User();
        switch (type){
            // 校验用户名
            case 1:
                user.setUsername(data);
                break;
            // 校验手机号
            case 2:
                user.setPhone(data);
                break;
            // 其他，参数错误
            default:
                throw new LyException(ExceptionEnum.INVALID_USER_DATA_TYPE_ERROR);
        }
        int count = userMapper.selectCount(user);
        return count == 0;
    }

    /**
     * 发送短信验证码
     * @param phone
     */
    public void sendCode(String phone) {
        // 生成key
        String key = KEY_PREFIX + phone;
        // 生成随机验证码
        String code = NumberUtils.generateCode(6);
        Map<String, String> msg = new HashMap<>(16);
        msg.put("phone",phone);
        msg.put("code", code);
        // 使用AMQP调用短信微服务发送验证码
        template.convertAndSend("leyou.sms.exchange","sms.verify.code", msg);
        // 保存手机验证码(保存五分钟);
        redisTemplate.opsForValue().set(key,code,5, TimeUnit.DAYS);
    }

    /**
     * 用户注册
     */
    public void register(@Valid User user, String code) {
        // 校验短信验证码
        // 1.获取key
        String key = KEY_PREFIX + user.getPhone();
        // 2.通过key去redis里拿到验证码
        String codeCache = redisTemplate.opsForValue().get(key);
        // 3.将用户输入的code和redis里存的code进行对比，存在就验证通过
        if(!code.equals(codeCache)) {
            throw new LyException(ExceptionEnum.USER_CHECK_CODE_ERROR);
        }
        // 生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        // 对密码进行加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword() , salt));
        // 写入数据库
        user.setCreated(new Date());
        userMapper.insert(user);
    }

    /**
     * 根据用户名和密码查询用户
     */
    public User queryUsernameAndPassword(String username, String password) {
        // 先查询用户
        User record = new User();
        record.setUsername(username);
        User user = userMapper.selectOne(record);
        // 判断是否有该用户
        if(user == null) {
            throw new LyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }
        // 校验密码
        if(!StringUtils.equals(user.getPassword(), CodecUtils.md5Hex(password, user.getSalt()))) {
            throw new LyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }
        return user;
    }
}