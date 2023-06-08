package com.entando.springbootagenda.controller;

import com.entando.springbootagenda.SpringbootAgendaApplication;
import com.entando.springbootagenda.config.PostgreSqlTestContainer;
import com.entando.springbootagenda.model.entity.ContactEntity;
import com.entando.springbootagenda.model.record.ContactRecord;
import com.entando.springbootagenda.repository.ContactRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        contactRepository.saveAllAndFlush(contactsList);
    }

    @AfterEach
    public void reset() {
        contactRepository.deleteAll();
    }

    @Test
    @Transactional
    void getAllUsersShouldReturnTheCurrentOrderedListOfUsersByIdAsc() throws Exception {
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
    @Transactional
    void getUserWithItsIdShouldReturnTheCorrectUser() throws Exception {
        Long currentFirstContactId = contactsList.get(0).getId();
        Long currentSecondContactId = contactsList.get(1).getId();

        contactMockMvc
                .perform(get("/api/contacts/" + currentFirstContactId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(currentFirstContactId))
                .andExpect(jsonPath("$.name").value("Jon"))
                .andExpect(jsonPath("$.lastname").value("doe"))
                .andExpect(jsonPath("$.address").value("3 Av bridge street"))
                .andExpect(jsonPath("$.phone").value("+33145326745"));

        contactMockMvc
                .perform(get("/api/contacts/" + currentSecondContactId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(currentSecondContactId))
                .andExpect(jsonPath("$.name").value("Jane"))
                .andExpect(jsonPath("$.lastname").value("doe"))
                .andExpect(jsonPath("$.address").value("7 East Side broke"))
                .andExpect(jsonPath("$.phone").value("+01545822705"));
    }

    @Test
    @Transactional
    void getUserWithId1234ShouldThrowANotFoundException() throws Exception {
        contactMockMvc
                .perform(get("/api/contacts/1234").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteUserWithId1ShouldDeleteTheUserInDb() throws Exception {
        contactMockMvc
                .perform(delete("/api/contacts/1")
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertThat(contactRepository.findOneById(1L)).isNotPresent();
    }

    @Test
    @Transactional
    void deleteANonExistingShouldReturnA204() throws Exception {
        contactMockMvc
                .perform(delete("/api/contacts/1234")
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void createContactWithAllFieldsSet() throws Exception {
        contactMockMvc
                .perform(post("/api/contact")
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(toJSON(new ContactRecord(null, "John", "Doe", "address", "+391234567")))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("/api/contacts/*"));
    }

    @Test
    void createContactWithinvalidData() throws Exception {
        contactMockMvc
                .perform(post("/api/contact")
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    @Transactional
    void updateAContactShouldUpdateTheDatabase() throws Exception {
        Long currentFirstContactId = contactsList.get(0).getId();
        ContactRecord contactUpdated = new ContactRecord(currentFirstContactId, "new name", "new lastname", "new address", "new phone");

        contactMockMvc
                .perform(put("/api/contacts/" + currentFirstContactId)
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(toJSON(contactUpdated))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        ContactEntity contactSavedFromDb =  contactRepository.findOneById(currentFirstContactId).get();

        assertThat(contactSavedFromDb.getName()).isEqualTo("new name");
        assertThat(contactSavedFromDb.getLastname()).isEqualTo("new lastname");
        assertThat(contactSavedFromDb.getAddress()).isEqualTo("new address");
        assertThat(contactSavedFromDb.getPhone()).isEqualTo("new phone");
    }

    @Test
    @Transactional
    void updateAContactWithAnUnknownIdShouldReturnANotFoundCode() throws Exception {
        ContactRecord contactUpdated = new ContactRecord(Long.MAX_VALUE, "new name", "new lastname", "new address", "new phone");

        contactMockMvc
                .perform(put("/api/contacts/" + Long.MAX_VALUE)
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(toJSON(contactUpdated))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateARecordWithANullIdShouldReturnABadRequestCode() throws Exception {
        ContactRecord contactUpdated = new ContactRecord(null, "", "", "", "");

        contactMockMvc
                .perform(put("/api/contacts/null")
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(toJSON(contactUpdated))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void updateARecordWithDifferentIdsABadRequestCode() throws Exception {
        ContactRecord contactUpdated = new ContactRecord(1L, "", "", "", "");

        contactMockMvc
                .perform(put("/api/contacts/2")
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(toJSON(contactUpdated))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    private static String toJSON(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
