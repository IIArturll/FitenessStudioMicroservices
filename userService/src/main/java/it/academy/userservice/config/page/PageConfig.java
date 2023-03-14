package it.academy.userservice.config.page;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

@Configuration
public class PageConfig {

    @Autowired
    public void configure(ObjectMapper objectMapper){
        objectMapper.addMixIn(Page.class,PageOutput.class);
    }
}
