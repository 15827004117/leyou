package com.leyou.client;

import com.leyou.item.pojo.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author 李静
 * @date 2019/9/2 12:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryClientTest {

    @Autowired
    private CategoryClient client;

    @Test
    public void queryCategoryByIds() {
        List<Category> list = client.queryCategoryByIds(Arrays.asList(1L, 2L, 3L));
        Assert.assertEquals(3, list.size());
       list.forEach(System.out::println);
    }
}
