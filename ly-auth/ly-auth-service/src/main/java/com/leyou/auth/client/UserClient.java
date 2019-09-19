package com.leyou.auth.client;

import leyou.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author 李静
 * @date 2019/9/18 16:46
 */
@FeignClient("user-service")
public interface UserClient extends UserApi {
}
