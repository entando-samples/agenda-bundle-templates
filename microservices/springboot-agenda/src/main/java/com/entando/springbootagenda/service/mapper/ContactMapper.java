package com.entando.springbootagenda.service.mapper;

import com.entando.springbootagenda.model.entity.ContactEntity;
import com.entando.springbootagenda.model.record.ContactRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public ContactEntity toEntity(ContactRecord record) {
        ContactEntity entity = new ContactEntity();
        BeanUtils.copyProperties(record, entity);
        return entity;
    }

    public ContactRecord toRecord(ContactEntity entity) {
        return new ContactRecord(entity);
    }

}
