package com.maxel.cursomc.config;

import com.maxel.cursomc.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    DBService dbService;

    @Bean
    public  boolean instantiateDatabase() throws Exception {
        dbService.instantiateTestDatabase();
        return  true;
    }
}
