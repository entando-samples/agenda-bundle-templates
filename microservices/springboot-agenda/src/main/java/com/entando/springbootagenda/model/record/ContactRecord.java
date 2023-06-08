package com.entando.springbootagenda.model.record;

import com.entando.springbootagenda.model.entity.ContactEntity;

public record ContactRecord(Long id, String name, String lastname, String address, String phone) {

    public ContactRecord(ContactEntity contactEntity) {
        this(contactEntity.getId(), contactEntity.getName(), contactEntity.getLastname(), contactEntity.getAddress(), contactEntity.getPhone());
    }
}
