package com.leyou.item.service;

import com.leyou.item.mapper.CategroyMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategroyService {

    @Autowired
    private CategroyMapper categroyMapper;

    /**
     * 根据parentId查询子类目
     * @param pid
     * @return
     */
    public List<Category> queryCategoryListByParentId(Long pid) {
        Category record = new Category();
        record.setParentId(pid);
        return categroyMapper.select(record);
    }

}
