package com.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
@Table(name = "tb_sku")
public class Sku {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long spuId;
    private String title;
    private String images;
    private Long price;
    /**商品特殊规格的键值对*/
    private String ownSpec;
    /**商品特殊规格的下标*/
    private String indexes;
    /**是否有效，逻辑删除用*/
    private Boolean enable;
    /**创建时间*/
    private Date createTime;
    /**最后修改时间*/
    private Date lastUpdateTime;
    /**库存*/
    @Transient
    private Integer stock;
}