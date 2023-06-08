package com.entando.springbootagenda.service.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.entando.springbootagenda.model.entity.ContactEntity;
import com.entando.springbootagenda.model.record.ContactRecord;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

public class ContactMapperTest {

    private ContactMapper mapper = new ContactMapper();

    @Test
    public void testToEntityFull() {
        final ContactRecord record = getContactRecordForTest();
        final ContactEntity entity = mapper.toEntity(record);
        assertNotNull(entity);
        assertEquals(2677, (long)entity.getId());
        assertEquals("John", entity.getName());
        assertEquals("Doe", entity.getLastname());
        assertEquals("Pennylane ave.", entity.getAddress());
        assertEquals("+331234567", entity.getPhone());
    }

    @Test
    public void toRecordFull() {
        final ContactEntity entity = getContactEntityForTest();
        final ContactRecord record = mapper.toRecord(entity);

        assertNotNull(entity);
        assertEquals(2677, (long)record.id());
        assertEquals("John", record.name());
        assertEquals("Doe", record.lastname());
        assertEquals("Pennylane ave.", record.address());
        assertEquals("+331234567", record.phone());
    }
    private ContactRecord getContactRecordForTest() {
        return new ContactRecord(getContactEntityForTest());
    }

    private ContactEntity getContactEntityForTest() {
        ContactEntity entity = new ContactEntity();

        entity.setId(2677L);
        entity.setName("John");
        entity.setLastname("Doe");
        entity.setAddress("Pennylane ave.");
        entity.setPhone("+331234567");
        return entity;
    }
}
