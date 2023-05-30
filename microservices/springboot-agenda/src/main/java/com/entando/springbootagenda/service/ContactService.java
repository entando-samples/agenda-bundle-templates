package com.entando.springbootagenda.service;

import com.entando.springbootagenda.model.record.ContactRecord;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactService {

    Page<ContactRecord> getAllContacts(Pageable pageable);

    Optional<ContactRecord> getContact(Long id);

    void delete(Long id);

    ContactRecord save(ContactRecord contact);

    void updateContact(ContactRecord contactRecord);
}
