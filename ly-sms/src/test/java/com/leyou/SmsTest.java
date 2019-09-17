package com.leyou;

import com.leyou.sms.LySmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李静
 * @date 2019/9/17 11:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySmsService.class)
public class SmsTest {

    @Autowired
    private AmqpTemplate template;

    @Test
    public void test() {
        Map<String, String> msg = new HashMap<>(16);
        msg.put("phone","15827004117");
        msg.put("code", "654321");
        template.convertAndSend("leyou.sms.exchange","sms.verify.code", msg);

    }


}
