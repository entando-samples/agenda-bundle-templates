package com.entando.springbootagenda.service;

import com.entando.springbootagenda.model.entity.ContactEntity;
import com.entando.springbootagenda.model.record.ContactRecord;
import com.entando.springbootagenda.repository.ContactRepository;
import com.entando.springbootagenda.service.mapper.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public final ContactMapper contactMapper;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
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
    public void delete(Long id) {
        contactRepository.findOneById(id).ifPresent(contactRepository::delete);
    }

    @Override
    public ContactRecord save(ContactRecord contact) {
        ContactEntity entity = contactMapper.toEntity(contact);
        ContactEntity saved = contactRepository.save(entity);
        return new ContactRecord(saved);
    }

    @Override
    public ContactRecord update(ContactRecord contactRecord) {
        ContactEntity contact = contactMapper.toEntity(contactRecord);
        contactRepository.save(contact);

        return contactMapper.toRecord(contact);
    }

    @Override
    public boolean exists(Long id) {
        return contactRepository.existsById(id);
    }
}
