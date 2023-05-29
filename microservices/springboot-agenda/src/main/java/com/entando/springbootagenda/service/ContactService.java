package com.entando.springbootagenda.service;

import com.entando.springbootagenda.model.record.ContactRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ContactService {

    Page<ContactRecord> getAllContacts(Pageable pageable);

    Optional<ContactRecord> getContact(Long id);
}
