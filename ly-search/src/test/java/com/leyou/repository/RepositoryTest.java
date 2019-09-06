package com.leyou.repository;

import com.leyou.LySearchService;
import com.leyou.client.GoodsClient;
import com.leyou.dao.GoodsRepository;
import com.leyou.item.pojo.Spu;
import com.leyou.pojo.Goods;
import com.leyou.service.SearchService;
import com.leyou.vo.PageResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySearchService.class)
public class RepositoryTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SearchService searchService;

    @Test
    public void createIndex(){
        // 创建索引
        this.elasticsearchTemplate.createIndex(Goods.class);
        // 配置映射
        this.elasticsearchTemplate.putMapping(Goods.class);
    }

    @Test
    public void loadData() {
        int page = 1;
        int rows = 100;
        int size = 0;
            do {
                // 查询spu
                PageResult<Spu> spuPageResult = goodsClient.querySpuByPage(page, rows, true, null);
                List<Spu> spus = spuPageResult.getItems();
                if(CollectionUtils.isEmpty(spus)) {
                    break;
                }
                // 构筑goods信息
                // 创建Goods集合
                List<Goods> goodsList = new ArrayList<>();
                // 遍历spu
                for (Spu spu : spus) {
                    try {
                        Goods goods = this.searchService.buildGoods(spu);
                        goodsList.add(goods);
                    } catch (Exception e) {
                        break;
                    }
                }
                // 放入索引库
                goodsRepository.saveAll(goodsList);
                page ++;
                size = spus.size();
            }while (size == 100);
    }
}
