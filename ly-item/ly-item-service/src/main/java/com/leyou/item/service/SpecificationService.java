package com.leyou.item.service;

import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.item.mapper.SpecificationMapper;
import com.leyou.item.pojo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


/**
 * 规格参数
 * @author 李静
 * @date 2019/8/23 11:15
 */
@Service
@Transactional
public class SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    /**
     * 根据id查询规格参数
     */
    public Specification queryById(Long cid) {
        Specification specification = specificationMapper.selectByPrimaryKey(cid);
        if(specification == null) {
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOND);
        }
        return specification;
    }
}
