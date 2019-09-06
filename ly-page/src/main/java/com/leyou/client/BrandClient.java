package com.leyou.client;

import com.leyou.item.pojo.Brand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: cuzz
 * @Date: 2018/11/9 16:21
 * @Description:
 */
@FeignClient("item-service")
public interface BrandClient{

    @GetMapping("brand/{id}")
    Brand queryBrandById(@PathVariable("id") Long id);

    /**
     * 根据id列表查询品牌列表
     * @param ids
     * @return
     */
    @GetMapping("brand/list")
    List<Brand> queryBrandByIds(@RequestParam("ids") List<Long> ids);
}
