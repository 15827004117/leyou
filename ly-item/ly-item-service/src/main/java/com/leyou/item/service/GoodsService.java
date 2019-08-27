package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 李静
 * @date 2019/8/27 10:46
 */
@Service
@Transactional
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private CategroyService categroyService;

    @Autowired
    private BrandService brandService;

    /**
     *  分页查询商品列表
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return
     */
    public Object querySpuByPage(Integer page, Integer rows, Boolean saleable, String key) {
        // 分页条件
        PageHelper.startPage(page, rows);
        // 创建过滤条件
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        // 1. 模糊搜索
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title", "%" + key + "%");
        }
        // 2.上下架
        if (saleable != null) {
            criteria.orEqualTo("saleable", saleable);
        }
        // 3.排序，默认降序
        example.setOrderByClause("last_update_time DESC");
        // 查询
        List<Spu> list = spuMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.GOODS_NOT_FOND);
        }
        // 解析品牌和分类名称
        loadCategoryAndBrandName(list);
        // 解析分页结果
        PageInfo<Spu> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    /**
     * 解析品牌和分类名称
     */
    private void loadCategoryAndBrandName(List<Spu> list) {
        for (Spu spu : list) {
            // 处理分类名称
            List<String> names = categroyService.queryNameByIds(
                    Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream().map(Category::getName).collect(Collectors.toList());
            spu.setCname(StringUtils.join(names, "/"));
            // 处理品牌名称
            Brand brand = brandService.queryById(spu.getBrandId());
            spu.setBname(brand.getName());
        }
    }
}
