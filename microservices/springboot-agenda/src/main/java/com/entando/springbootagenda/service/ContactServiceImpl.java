package com.entando.springbootagenda.service;

import com.entando.springbootagenda.model.record.ContactRecord;
import com.entando.springbootagenda.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Page<ContactRecord> getAllContacts(Pageable pageable) {
        return contactRepository.findAll(pageable).map(ContactRecord::new);
    }

    @Override
    public Optional<ContactRecord> getContact(Long id) {
        return contactRepository.findOneById(id).map(ContactRecord::new);
    }

    @Override
    public Optional<ContactDto> getContact(Long id) {
        return contactRepository.findOneById(id).map(ContactDto::new);
    }

}
