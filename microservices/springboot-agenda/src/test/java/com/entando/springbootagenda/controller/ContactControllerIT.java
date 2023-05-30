package com.entando.springbootagenda.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.entando.springbootagenda.SpringbootAgendaApplication;
import com.entando.springbootagenda.config.PostgreSqlTestContainer;
import com.entando.springbootagenda.model.entity.ContactEntity;
import com.entando.springbootagenda.model.record.ContactRecord;
import com.entando.springbootagenda.repository.ContactRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

@AutoConfigureMockMvc
@SpringBootTest(classes = {SpringbootAgendaApplication.class})
@Testcontainers
class ContactControllerIT extends PostgreSqlTestContainer {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private MockMvc contactMockMvc;

    List<ContactEntity> contactsList = new ArrayList<>();

    @BeforeEach
    public void init() {
        contactsList.add(new ContactEntity(null, "Jon", "doe", "3 Av bridge street", "+33145326745"));
        contactsList.add(new ContactEntity(null, "Jane", "doe", "7 East Side broke", "+01545822705"));
    }

    @AfterEach
    public void reset() {
        contactRepository.deleteAll();
    }

    @Test
    @Transactional
    void getAllUsersShouldReturnTheCurrentOrderedListOfUsersByIdAsc() throws Exception {
        contactRepository.saveAllAndFlush(contactsList);

        contactMockMvc
                .perform(get("/api/contacts?sort=id,asc").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[0].id").isNotEmpty())
                .andExpect(jsonPath("$.[0].name").value("Jon"))
                .andExpect(jsonPath("$.[0].lastname").value("doe"))
                .andExpect(jsonPath("$.[0].address").value("3 Av bridge street"))
                .andExpect(jsonPath("$.[0].phone").value("+33145326745"))
                .andExpect(jsonPath("$.[1].id").isNotEmpty())
                .andExpect(jsonPath("$.[1].name").value("Jane"))
                .andExpect(jsonPath("$.[1].lastname").value("doe"))
                .andExpect(jsonPath("$.[1].address").value("7 East Side broke"))
                .andExpect(jsonPath("$.[1].phone").value("+01545822705"));
    }

    @Test
    @Transactional
    void getAllUsersShouldReturnTheCurrentOrderedListOfUsersByNameAsc() throws Exception {
        contactRepository.saveAllAndFlush(contactsList);

        contactMockMvc
                .perform(get("/api/contacts?sort=name,asc").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[0].id").isNotEmpty())
                .andExpect(jsonPath("$.[0].name").value("Jane"))
                .andExpect(jsonPath("$.[0].lastname").value("doe"))
                .andExpect(jsonPath("$.[0].address").value("7 East Side broke"))
                .andExpect(jsonPath("$.[0].phone").value("+01545822705"))
                .andExpect(jsonPath("$.[1].id").isNotEmpty())
                .andExpect(jsonPath("$.[1].name").value("Jon"))
                .andExpect(jsonPath("$.[1].lastname").value("doe"))
                .andExpect(jsonPath("$.[1].address").value("3 Av bridge street"))
                .andExpect(jsonPath("$.[1].phone").value("+33145326745"));
    }

    @Test
    void createContactWithAllFieldsSet() throws Exception {
        contactMockMvc
                .perform(post("/api/contact").accept(MediaType.APPLICATION_JSON)
                        .content(toJSON(new ContactRecord(null, "John", "Doe", "address", "+391234567")))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.lastname").value("Doe"))
                .andExpect(jsonPath("$.address").value("address"))
                .andExpect(jsonPath("$.phone").value("+391234567"));
    }


    public static String toJSON(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
