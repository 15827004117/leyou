package com.leyou.client;

import com.leyou.item.pojo.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: lijing
 * @Date: 2018/11/9 15:50
 * @Description:
 */
@FeignClient("item-service")
public interface CategoryClient{

    /**
     * 查询category
     * @param ids
     * @return
     */
    @GetMapping("category/list/ids")
    List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);
}
