package com.entando.springbootagenda.service.mapper;

import com.entando.springbootagenda.model.entity.ContactEntity;
import com.entando.springbootagenda.model.record.ContactRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public ContactEntity toEntity(ContactRecord recordIn) {
        ContactEntity entityOut = new ContactEntity();
        BeanUtils.copyProperties(recordIn, entityOut);
        return entityOut;
    }

    public ContactRecord toRecord(ContactEntity entity) {
        return new ContactRecord(entity);
    }

}
