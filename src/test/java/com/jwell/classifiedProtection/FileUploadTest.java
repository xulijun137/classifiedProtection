package com.jwell.classifiedProtection;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ClassifiedProtectionApplication.class)
public class FileUploadTest {

    private static Logger logger = LoggerFactory.getLogger(FileUploadTest.class);

    private MockMvc mockMvc;

}
