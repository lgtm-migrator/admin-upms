package com.hb0730.admin.upms.server.system;

import com.hb0730.commons.spring.SpringContextUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author bing_huang
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBaseTest {
    @Autowired
    private ApplicationContext context;

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
        SpringContextUtils.setApplicationContext(context);
    }

}
