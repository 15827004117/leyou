package leyou.user.api;

import leyou.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 李静
 * @date 2019/9/18 16:45
 */
public interface UserApi {

    /**
     * 根据用户名和密码查询用户
     */
    @GetMapping("query")
    User queryUsernameAndPassword(@RequestParam("username") String username,
                                  @RequestParam("password") String password);
}
