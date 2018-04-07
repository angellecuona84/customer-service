package com.everymundo;

import com.everymundo.controller.CustomerController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerControllerTest {

    @Autowired
    private CustomerController customerController;

    @Test
    public void controllerInitializedCorrectly() {
        assertNotNull(customerController);
    }

}
