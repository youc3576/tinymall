package com.menethil.tinymall.wx;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ServerConfigTest {

    @Value("${server.url}")
    private String address;

    @Value("${server.port}")
    private Integer port;

    @Value("${wx.notify-url}")
    private String notifyUrl;

    @Value("${tinymall.core.store.activeStorage}")
    private String activeStorage;

    @Test
    public void test() {

        System.out.println("server.url = " + address + "; server.port = " + port);
        System.out.println("notify-url = " + notifyUrl);
        System.out.println("activeStorage = " + activeStorage);
    }
}