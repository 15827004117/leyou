package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategroyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategroyController {

    @Autowired
    private CategroyService categroyService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategroyListByParentId(@RequestParam(value = "pid", defaultValue = "0") Long pid) {
        List<Category> list = categroyService.queryCategoryListByParentId(pid);
        if(list == null){
            // 无数据,返回404状态码
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
            return ResponseEntity.ok(list);
    }
}
