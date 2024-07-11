package com.example.phonebook.domain.ports.api;

import com.example.phonebook.application.request.ContactRequest;
import com.example.phonebook.domain.data.ContactResultDTO;
import com.example.phonebook.infrastructure.exception.ContactException;
import org.springframework.stereotype.Service;

@Service
public interface ContactServicePort {
    
    ContactResultDTO save(ContactRequest contactRequest) throws ContactException;
    ContactResultDTO get(String param) throws ContactException;
    void update(int contactId, String name, String phone, String email) throws ContactException;
    void delete(int contactId) throws ContactException;
}
