package com.example.phonebook.domain.ports.spi;

import com.example.phonebook.domain.data.ContactDTO;
import com.example.phonebook.infrastructure.exception.ContactException;

public interface ContactPersistencePort {

    Integer save(ContactDTO contactDTO) throws ContactException;
    ContactDTO get(String param) throws ContactException;
    Integer update(int contactId, String name, String phone, String email) throws ContactException;
    Integer delete(int contactId) throws ContactException;
}
