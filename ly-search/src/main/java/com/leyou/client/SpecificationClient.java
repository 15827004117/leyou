package com.leyou.client;

import com.leyou.item.pojo.SpecParam;
import com.leyou.item.pojo.Specification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: cuzz
 * @Date: 2018/11/9 16:20
 * @Description:
 */
@FeignClient("item-service")
public interface SpecificationClient{

    /**
     * 根据分类id查询规格组参数
     * @param cid
     * @return
     */
    @GetMapping("spec/{cid}")
    Specification querySpecificationByCategoryId(@PathVariable("cid")Long cid);

    @GetMapping("spec/params")
    List<SpecParam> querySpecParams(@RequestParam(value = "gid",required = false)Long gid,
                                    @RequestParam(value = "cid",required = false)Long cid,
                                    @RequestParam(value = "searching",required = false)Boolean searching);
}
