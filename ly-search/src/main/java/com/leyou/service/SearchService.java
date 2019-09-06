package com.leyou.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.client.BrandClient;
import com.leyou.client.CategoryClient;
import com.leyou.client.GoodsClient;
import com.leyou.client.SpecificationClient;
import com.leyou.dao.GoodsRepository;
import com.leyou.enums.ExceptionEnum;
import com.leyou.exception.LyException;
import com.leyou.item.pojo.*;
import com.leyou.pojo.Goods;
import com.leyou.pojo.SearchRequest;
import com.leyou.utils.JsonUtils;
import com.leyou.vo.PageResult;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
public class SearchService {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private SpecificationClient specificationClient;

    @Autowired
    private GoodsRepository goodsRepository;

    /**
     * 添加数据
     * @param spu
     * @return
     * @throws Exception
     */
    public Goods buildGoods(Spu spu)  throws Exception{
        Goods goods = new Goods();

        // 查询商品分类名称
        List<Category> categories = categoryClient.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        if(CollectionUtils.isEmpty(categories)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOND);
        }
        List<String> names = categories.stream().map(Category::getName).collect(Collectors.toList());

        // 查询品牌
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        if (brand == null) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOND);
        }

        // 查询sku
        List<Sku> skuList = this.goodsClient.querySkuList(spu.getId());
        if(CollectionUtils.isEmpty(skuList)){
            throw new LyException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
        }
        // 处理sku
        List<Map<String, Object>> skus = new ArrayList<>();
        // 价格集合
        Set<Long> priceSet = new HashSet<>();
        for (Sku sku : skuList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", sku.getId());
            map.put("title", sku.getTitle());
            map.put("price", sku.getPrice());
            map.put("image", StringUtils.substringBefore(sku.getImages(),","));
            skus.add(map);
            // 处理价格
            priceSet.add(sku.getPrice());
        }

        // 查询详情
        SpuDetail spuDetail = this.goodsClient.querySpuDetailById(spu.getId());
        // 查询规格参数
        List<SpecParam> params = this.specificationClient.querySpecParams(null, spu.getCid3(), true);
        // 处理规格参数

        Map<Long, String> genericSpec = JsonUtils.parseMap(spuDetail.getGenericSpec(), Long.class, String.class);
        //获取特有规格参数
        Map<Long, List<String>> specialSpec = JsonUtils.nativeRead(
                spuDetail.getSpecialSpec(), new TypeReference<Map<Long, List<String>>>() {});
        // 过滤规格模板，把所有可搜索的信息保存到Map中
        //将参数填入map
        Map<String,Object> specs = new HashMap<>();
        for (SpecParam param : params) {
            // 规格名字 key
            String key = param.getName();
            Object value = "";
            //规格参数 value
            if(param.getGeneric()){
                // 通用属性
                // 通用参数的数值类型有分段的情况存在，要做一个处理,不能按上面那种方法获得value
                value = genericSpec.get(param.getId());
                //判断是否为数值类型 处理成段,覆盖之前的value
                if(param.getNumeric()){
                    value = chooseSegment(value.toString(),param);
                }
            }else {
                // 特殊属性
                value = specialSpec.get(param.getId());
            }
            value = (value == null ? "其他" : value);

            specs.put(key,value);
        }
        goods.setId(spu.getId());
        goods.setSubTitle(spu.getSubTitle());
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setAll(spu.getTitle() + StringUtils.join(names, " ") + brand.getName());
        goods.setPrice(priceSet);
        goods.setSkus(JsonUtils.serialize(skuList));
        goods.setSpecs(specs);
        return goods;
    }

    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

    /**
     * 搜索商品
     * @param request
     * @return
     */
    public PageResult<Goods> search(SearchRequest request) {
        int page = request.getPage() -1;
        int size = request.getSize();

        // 1.构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 2.通过sourceFilter设置返回的结果字段,我们只需要id、skus、subTitle
        queryBuilder.withSourceFilter(new FetchSourceFilter(
                new String[]{"id","skus","subTitle"}, null));
        // 3.分页
        queryBuilder.withPageable(PageRequest.of(page, size));
        // 4.过滤
        queryBuilder.withQuery(QueryBuilders.matchQuery("all", request.getKey()));
        // 5.查询，获取结果
        Page<Goods> pageInfo = goodsRepository.search(queryBuilder.build());
        // 6.解析结果
        long total = pageInfo.getTotalElements();
        long totalPage = pageInfo.getTotalPages();
        List<Goods> goodsList = pageInfo.getContent();
        // 7.封装结果并返回
        return new PageResult<>(total, totalPage, goodsList);
    }

}