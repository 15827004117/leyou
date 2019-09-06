package com.leyou.dao;

import com.leyou.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Administrator
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
