package com.leyou.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator
 */
@Data
@Document(indexName = "goods", type = "docs", shards = 1, replicas = 0)
public class Goods {
    /**spuId*/
    @Id
    private Long id;
    /**所有需要被搜索的信息，包含标题，分类，甚至品牌*/
    @Field(type = FieldType.text, analyzer = "ik_max_word")
    /**卖点*/
    private String all;
    @Field(type = FieldType.keyword, index = false)
    private String subTitle;
    /**品牌id*/
    private Long brandId;
    /**1级分类id*/
    private Long cid1;
    /**2级分类id*/
    private Long cid2;
    /**3级分类id*/
    private Long cid3;
    /**创建时间*/
    private Date createTime;
    /**价格*/
    private Set<Long> price;
    /**sku信息的json结构*/
    @Field(type = FieldType.keyword, index = false)
    private String skus;
    /**可搜索的规格参数，key是参数名，值是参数值*/
    private Map<String, Object> specs;

    public Goods() {
    }

    public Goods(Long id, String all, String subTitle, Long brandId, Long cid1, Long cid2, Long cid3, Date createTime, Set<Long> price, String skus, Map<String, Object> specs) {
        this.id = id;
        this.all = all;
        this.subTitle = subTitle;
        this.brandId = brandId;
        this.cid1 = cid1;
        this.cid2 = cid2;
        this.cid3 = cid3;
        this.createTime = createTime;
        this.price = price;
        this.skus = skus;
        this.specs = specs;
    }
}