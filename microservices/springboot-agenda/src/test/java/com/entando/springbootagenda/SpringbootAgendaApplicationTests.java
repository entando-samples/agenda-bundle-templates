package com.entando.springbootagenda;

import static org.assertj.core.api.Assertions.assertThat;

import com.entando.springbootagenda.service.mapper.ContactMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SpringbootAgendaApplicationTests {

    @Test
    void contextLoads(ApplicationContext context) {
        // avoid code smell
        assertThat(context.getBean(ContactMapper.class)).isNotNull();
    }

}
