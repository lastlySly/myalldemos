package com.lastlysly;

import com.lastlysly.utils.CustomAsposeWordsUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElectronicContractDemoApplicationTests {

    @Autowired
    private CustomAsposeWordsUtils customAsposeWordsUtils;
    @Test
    public void contextLoads() {
    }

    @Test
    public void test1(){
        Map map = CustomAsposeWordsUtils.getDataMap();
        customAsposeWordsUtils.createPdfToPath("C:/Users/lastlySly/Desktop/1.pdf",map);
    }

}
