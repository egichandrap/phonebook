package com.example.phonebook.domain.service;

import com.example.phonebook.application.request.ContactRequest;
import com.example.phonebook.domain.data.ContactDTO;
import com.example.phonebook.domain.data.ContactResultDTO;
import com.example.phonebook.domain.ports.api.ContactServicePort;
import com.example.phonebook.domain.ports.spi.ContactPersistencePort;
import com.example.phonebook.infrastructure.exception.ContactException;
import com.example.phonebook.infrastructure.util.ContactHttpStatus;
import com.example.phonebook.infrastructure.util.Util;

import java.sql.Statement;
import java.util.Date;

public class ContactServiceImpl implements ContactServicePort {

    private ContactPersistencePort contactPersistencePort;

    public ContactServiceImpl(ContactPersistencePort contactPersistencePort){
        this.contactPersistencePort = contactPersistencePort;
    }

    @Override
    public ContactResultDTO save(ContactRequest contactRequest) throws ContactException {
        try {
            ContactDTO contactDTO = new ContactDTO();
            contactDTO.setName(contactRequest.getName());
            contactDTO.setPhone_number(contactRequest.getPhone_number());
            contactDTO.setEmail(contactRequest.getEmail());
            contactDTO.setCreatedDate(new Date());

            Integer result = contactPersistencePort.save(contactDTO);
            return new ContactResultDTO(result, contactDTO);
        } catch (ContactException e){
            throw e;
        } catch (Exception exception){
            Throwable throwable = Util.lastThrowable(exception);
            Util.debugLogger.error("Save Data Contact Phone Book Error | {} | {}", throwable.getMessage(), throwable.getCause());
            throw  new ContactException(throwable.getMessage(), ContactHttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ContactResultDTO get(String param) throws ContactException {
        try {
            ContactDTO contactDTO = contactPersistencePort.get(param);
            return new ContactResultDTO(Statement.RETURN_GENERATED_KEYS, contactDTO);
        } catch (ContactException e){
            throw e;
        } catch (Exception exception){
            Throwable throwable = Util.lastThrowable(exception);
            Util.debugLogger.error("Get Data Contact Error With Param {} | {} | {}", param, throwable.getMessage(), throwable.getCause());
            throw new ContactException(throwable.getMessage(), ContactHttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void update(int contactId, String name, String phone, String email) throws ContactException {
        try {
            Integer result = contactPersistencePort.update(contactId, name, phone, email);
        } catch (ContactException e){
            throw e;
        } catch (Exception exception){
            Throwable throwable = Util.lastThrowable(exception);
            Util.debugLogger.error("Update Data Contact Error {} | {}", throwable.getMessage(), throwable.getCause());
            throw new ContactException(throwable.getMessage(), ContactHttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(int contactId) throws ContactException {
        try {
            contactPersistencePort.delete(contactId);
        } catch (Exception e){
            Throwable throwable = Util.lastThrowable(e);
            Util.debugLogger.error("Delete Data Contact Error {} | {}", throwable.getMessage(), throwable.getCause());
            throw new ContactException(throwable.getMessage(), ContactHttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
