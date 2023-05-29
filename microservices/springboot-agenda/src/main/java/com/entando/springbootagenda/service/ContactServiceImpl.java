package com.entando.springbootagenda.service;

import com.entando.springbootagenda.repository.ContactRepository;
import com.entando.springbootagenda.service.dto.ContactDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Page<ContactDto> getAllContacts(Pageable pageable) {
        return contactRepository.findAll(pageable).map(ContactDto::new);
    }

}
