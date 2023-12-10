package com.kirito.management;

import com.kirito.management.utils.ThreadLocalUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ThreadLocalTest {

    @Test
    public void setUtils() {
        ThreadLocal utils=new ThreadLocal();
        utils.set("aaaa");
        System.out.println(utils.get());
        utils.set("bbb");
        System.out.println(utils.get());
        System.out.println(utils.get());
        System.out.println(utils.get());
        utils.remove();
    }
}
