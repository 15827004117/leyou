package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategroyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategroyController {

    @Autowired
    private CategroyService categroyService;

    /**
     * 查看商品列表
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategroyListByParentId(@RequestParam(value = "pid", defaultValue = "0") Long pid) {
        List<Category> list = categroyService.queryCategoryListByParentId(pid);
        if(list == null){
            // 无数据,返回404状态码
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
            return ResponseEntity.ok(list);
    }

    /**
     * 根据品牌id查询商品分类
     */
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryByBrandId(@PathVariable(value = "bid") Long bid) {
        List<Category> list = this.categroyService.queryByBrandId(bid);
        if (list == null || list.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }
}
