package com.leyou.item.service;

import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.mapper.SpecificationMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.pojo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 规格参数
 * @author 李静
 * @date 2019/8/23 11:15
 */
@Service
public class SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private SpecParamMapper paramMapper;

    @Autowired
    private SpecGroupMapper groupMapper;

    /**
     * 根据id查询规格参数
     */
    @Transactional(rollbackFor = Exception.class)
    public Specification queryById(Long cid) {
        Specification specification = specificationMapper.selectByPrimaryKey(cid);
        if(specification == null) {
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOND);
        }
        return specification;
    }

    /**
     * 查询规格组
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroupByCid(Long cid) {

        SpecGroup group = new SpecGroup();
        group.setCid(cid);

        List<SpecGroup> list = groupMapper.select(group);

        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.GROUP_NOT_FOUND);
        }

        return list;
    }

    /**
     * 查询指定规格参数
     * @param gid
     * @param cid
     * @param searching
     * @return
     */
    public List<SpecParam> querySpecParams(Long gid, Long cid, Boolean searching) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);
        List<SpecParam> params = paramMapper.select(param);

        if(CollectionUtils.isEmpty(params)){
            throw new LyException(ExceptionEnum.GROUP_PARAM_NOT_FOUND);
        }
        return params;
    }

    /**
     * 查询规格组及组内规格参数
     */
    public List<SpecGroup> querySpecsByCid(Long cid) {
        List<SpecGroup> specGroups = queryGroupByCid(cid);

        List<SpecParam> specParams = querySpecParams(null, cid, null);

        Map<Long, List<SpecParam>> map = new HashMap<>();
        //遍历specParams
        for (SpecParam param : specParams) {
            Long groupId = param.getGroupId();
            if (!map.keySet().contains(param.getGroupId())) {
                //map中key不包含这个组ID
                map.put(param.getGroupId(), new ArrayList<>());
            }
            //添加进map中
            map.get(param.getGroupId()).add(param);
        }
        for (SpecGroup specGroup : specGroups) {
            specGroup.setParams(map.get(specGroup.getId()));
        }
        return specGroups;
    }

    //新增规格组
    public void saveSpecGroup(SpecGroup specGroup) {
        int count = groupMapper.insert(specGroup);
        if (count != 1) {
            throw new LyException(ExceptionEnum.SPEC_GROUP_CREATE_FAILED);
        }
    }

    //删除规格组
    public void deleteSpecGroup(Long id) {
        if (id == null) {
            throw new LyException(ExceptionEnum.INVALID_PARAM);
        }
        SpecGroup specGroup = new SpecGroup();
        specGroup.setId(id);
        int count = groupMapper.deleteByPrimaryKey(specGroup);
        if (count != 1) {
            throw new LyException(ExceptionEnum.DELETE_SPEC_GROUP_FAILED);
        }
    }

    //修改规格组
    public void updateSpecGroup(SpecGroup specGroup) {
        int count = groupMapper.updateByPrimaryKey(specGroup);
        if (count != 1) {
            throw new LyException(ExceptionEnum.UPDATE_SPEC_GROUP_FAILED);
        }
    }


    //新增规格参数
    public void saveSpecParam(SpecParam specParam) {
        int count = paramMapper.insert(specParam);
        if (count != 1) {
            throw new LyException(ExceptionEnum.SPEC_PARAM_CREATE_FAILED);
        }
    }

    //修改规格参数
    public void updateSpecParam(SpecParam specParam) {
        int count = paramMapper.updateByPrimaryKeySelective(specParam);
        if (count != 1) {
            throw new LyException(ExceptionEnum.UPDATE_SPEC_PARAM_FAILED);
        }
    }

    //删除规格参数
    public void deleteSpecParam(Long id) {
        if (id == null) {
            throw new LyException(ExceptionEnum.INVALID_PARAM);
        }
        int count = paramMapper.deleteByPrimaryKey(id);
        if (count != 1) {
            throw new LyException(ExceptionEnum.DELETE_SPEC_PARAM_FAILED);
        }
    }

    /**
     *  根据cid查询规格组及其规格参数
     */
    public List<SpecGroup> queryListByCid(Long cid) {
        // 查询规格组
        List<SpecGroup> specGroups = queryGroupByCid(cid);

        // 查询组内参数
        List<SpecParam> specParams = querySpecParams(null, cid, null);

        // 先把规格参数变成map，map的key是组Id,map的值是组下的所有参数
        HashMap<Long, List<SpecParam>> map = new HashMap<>();
        for (SpecParam param : specParams) {
            if(!map.containsKey(param.getGroupId())){
                // 如果不包含组Id，说明是第一次出现,因此要新增一个List
                map.put(param.getGroupId(),new ArrayList<>());
            }
            // 如果group存在 也要添加list
            List<SpecParam> list = map.get(param.getGroupId());
            list.add(param);
        }

        // 填充param到group中
        for (SpecGroup specGroup : specGroups) {
            specGroup.setParams(map.get(specGroup.getId()));
        }

        return specGroups;
    }


}
