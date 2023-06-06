package com.entando.springbootagenda.controller;

import com.entando.springbootagenda.model.record.ContactRecord;
import com.entando.springbootagenda.service.ContactService;
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

import java.util.List;

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
      public ResponseEntity<ContactRecord> createContact(@RequestBody ContactRecord contact) {
          log.debug("REST request to create a NEW contact: " + contact);
          ContactRecord created = contactService.save(contact);
          return new ResponseEntity<>(created, HttpStatus.CREATED);
      }
}
