package com.leyou.item.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 规格参数
 * @author 李静
 * @date 2019/8/23 11:13
 */
@Table(name = "tb_specification")
public class Specification {

    @Id
    private Long categoryId;
    private String specifications;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }
}
