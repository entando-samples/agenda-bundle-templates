package com.entando.springbootagenda.service;

import com.entando.springbootagenda.service.dto.ContactDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactService {

    Page<ContactDto> getAllContacts(Pageable pageable);
}
