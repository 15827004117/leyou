package com.leyou.user.service;

import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.user.mapper.UserMapper;
import leyou.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

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
        int result = userMapper.selectCount(user);

        return result == 0;
    }
}