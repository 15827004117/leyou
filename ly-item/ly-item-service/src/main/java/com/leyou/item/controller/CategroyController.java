package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategroyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("categroy")
public class CategroyController {

    @Autowired
    private CategroyService categroyService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategroyListByParentId(
            @RequestParam(value = "pid", defaultValue = "0") Long pid) {
        return null;

    }
}
