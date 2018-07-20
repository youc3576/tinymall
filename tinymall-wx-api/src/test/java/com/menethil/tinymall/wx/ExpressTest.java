package com.menethil.tinymall.wx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.menethil.tinymall.core.express.ExpressService;
import com.menethil.tinymall.core.express.dao.ExpressInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ExpressTest {

    @Autowired
    ExpressService expressService;

    @Test
    public void test() {

        String result = null;
        try {
            result = expressService.getOrderTracesByJson("YTO", "800669400640887922");
            ObjectMapper objMap = new ObjectMapper();
            ExpressInfo ei = objMap.readValue(result, ExpressInfo.class);
            ei.getTraces();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print(result);
    }
}
