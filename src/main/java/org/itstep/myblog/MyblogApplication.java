package org.itstep.myblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
public class MyblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyblogApplication.class, args);
    }

}
