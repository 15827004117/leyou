package com.leyou.item.controller;

import com.leyou.item.pojo.Specification;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 规格参数
 * @author 李静
 * @date 2019/8/23 11:15
 */
@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据分类id查询规格组参数
     * @param cid
     * @return
     */
    @GetMapping("/{cid}")
    public ResponseEntity<Specification> querySpecificationByCategoryId(@PathVariable("cid")Long cid) {
        return ResponseEntity.ok(specificationService.queryById(cid));
    }
}
