package com.maxel.cursomc.config;

import com.maxel.cursomc.service.DBService;
import com.maxel.cursomc.service.EmailService;
import com.maxel.cursomc.service.MockEmailService;
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

    //É um componente de sistema
    //Sempre quando for injetar a instância de um EmailService no profile de teste o spring vai procurar, e se tiver, usar a instância desse componente
    @Bean
    public EmailService emailService() {
        return  new MockEmailService();
    }
}
