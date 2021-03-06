package com.leyou.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;

/**
 * @author lijing
 */
@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 分页、搜索查询
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    public PageResult<Brand> queryBrandByPageAndSort(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        // 开始分页
        PageHelper.startPage(page, rows);
        // 过滤
        Example example = new Example(Brand.class);
        if (!"".equals(key) && key != null) {
            example.createCriteria().andLike("name", "%" + key + "%")
                    .orEqualTo("letter", key);
        }
        if (!"".equals(sortBy) && sortBy != null) {
            // 排序
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        // 查询
        Page<Brand> pageInfo = (Page<Brand>) brandMapper.selectByExample(example);
        // 返回结果
        return new PageResult<>(pageInfo.getTotal(), pageInfo);
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveBrand(Brand brand, List<Integer> cids) {
        // 新增品牌
        brand.setId(null);
        int result = brandMapper.insertSelective(brand);
        if(result <= 0) {
            throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
        }
        // 新增中间表
        for (Integer cid : cids) {
            int result2 = brandMapper.saveCategoryBrand(cid, brand.getId());
            if(result2 <= 0) {
                throw new LyException(ExceptionEnum.CATEGORY_BRAND_SAVE_ERROR);
            }
        }
    }

    /**
     * 根据id查询品牌
     * @param brandId
     * @return
     */
    public Brand queryById(Long brandId) {
        Brand brand = brandMapper.selectByPrimaryKey(brandId);
        if(brand == null) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOND);
        }
        return brand;
    }

    /**
     * 根据分类查询品牌
     * @param cid
     * @return
     */
    public List<Brand> queryByCid(Long cid) {
        List<Brand> list = brandMapper.queryByCategoryId(cid);
        if(CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOND);
        }
        return list;
    }

    public List<Brand> queryBrandByIds(List<Long> ids) {
        List<Brand> list = brandMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return list;
    }
}
