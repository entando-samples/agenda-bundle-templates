package com.entando.springbootagenda.controller;

import com.entando.springbootagenda.model.record.ContactRecord;
import com.entando.springbootagenda.service.ContactService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController()
@RequestMapping("/api")
public class ContactController {
    private final Logger log = LoggerFactory.getLogger(ContactController.class);
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<ContactRecord>> getAllContacts(Pageable pageable) {
        log.debug("REST request to get all Contacts");

        final Page<ContactRecord> page = contactService.getAllContacts(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<ContactRecord> getContact(@PathVariable Long id) {
        log.debug("REST request to get the contact with id {}", id);

        return contactService
                .getContact(id)
                .map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id) {
        log.debug("REST request to delete contact: {}", id);

        contactService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/contact")
    public ResponseEntity<ContactRecord> createContact(@RequestBody ContactRecord contact) throws URISyntaxException {
        log.debug("REST request to create a NEW contact: {}", contact );

        ContactRecord created = contactService.save(contact);

        return ResponseEntity
                .created(new URI("/api/contacts/" + created.id()))
                .body(created);
    }

    @PutMapping("/contacts/{id}")
    public ResponseEntity<ContactRecord> updateContact(@PathVariable(value = "id") final Long id, @RequestBody ContactRecord contact) {
        log.debug("REST request to update contact: {}", id);

        if(id == null || !Objects.equals(id, contact.id())) {
            return ResponseEntity.badRequest().build();
        }
        if(!contactService.exists(id)) {
            return ResponseEntity.notFound().build();
        }

        ContactRecord savedContact = contactService.update(contact);

        return ResponseEntity.ok().body(savedContact);
    }
}
