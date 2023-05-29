package com.entando.springbootagenda.service.dto;

import com.entando.springbootagenda.model.entity.ContactEntity;

import java.util.Objects;

public class ContactDto {
    private Long id;

    private String name;

    private String lastname;

    private String address;

    private String phone;

    public ContactDto() {
    }

    public ContactDto(ContactEntity contactEntity) {
        this.id = contactEntity.getId();
        this.name = contactEntity.getName();
        this.lastname = contactEntity.getLastname();
        this.address = contactEntity.getAddress();
        this.phone = contactEntity.getPhone();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDto that = (ContactDto) o;
        return id.equals(that.id) && Objects.equals(name, that.name) && Objects.equals(lastname, that.lastname) && Objects.equals(address, that.address) && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastname, address, phone);
    }
}
