package com.entando.springbootagenda.repository;

import com.entando.springbootagenda.model.entity.ContactEntity;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, UUID>, PagingAndSortingRepository<ContactEntity, UUID> {

    Optional<ContactEntity> findOneById(Long id);

    boolean existsById(Long id);
}
