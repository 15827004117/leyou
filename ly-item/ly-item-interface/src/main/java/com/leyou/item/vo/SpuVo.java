package com.leyou.item.vo;

import lombok.Data;

import javax.persistence.Transient;
import java.util.Date;

/**
 * @author 李静
 * @date 2019/8/27 11:13
 */
@Data
public class SpuVo {

    private Long id;
    private Long brandId;
    /**1级类目*/
    private Long cid1;
    /**2级类目*/
    private Long cid2;
    /**3级类目*/
    private Long cid3;
    /**标题*/
    private String title;
    /**子标题*/
    private String subTitle;
    /**是否上架*/
    private Integer saleable;
    /**创建时间*/
    private Date createTime;

    /**商品分类名称*/
    @Transient
    private String cname;
    /**品牌名称*/
    @Transient
    private String bname;
}
