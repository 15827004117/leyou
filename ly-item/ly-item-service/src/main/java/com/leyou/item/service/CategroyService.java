package com.leyou.item.service;

import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.item.mapper.CategroyMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    /**
     * 根据品牌id查询商品分类
     */
    public List<Category> queryByBrandId(Long bid) {
        return this.categroyMapper.queryByBrandId(bid);
    }

    public List<Category> queryNameByIds(List<Long> ids) {
        List<Category> list = categroyMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.GOODS_NOT_FOND);
        }
        return list;
    }

}
