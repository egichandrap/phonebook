package com.example.phonebook.infrastructure.adapters;

import com.example.phonebook.domain.data.ContactDTO;
import com.example.phonebook.domain.ports.spi.ContactPersistencePort;
import com.example.phonebook.infrastructure.entity.Contact;
import com.example.phonebook.infrastructure.exception.ContactException;
import com.example.phonebook.infrastructure.repository.nonSpring.repository.ContactRepository;
import com.example.phonebook.infrastructure.util.ContactHttpStatus;
import com.example.phonebook.infrastructure.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ContactAdapter implements ContactPersistencePort {

    @Autowired
    ContactRepository contactRepository;

    @Override
    public Integer save(ContactDTO contactDTO) throws ContactException {
        Integer result = null;
        try {
            Contact contact = Util.INSTANCE_MAPPER.contactDTOtoContact(contactDTO);
            result = contactRepository.saveContact(contact);
        } catch (Exception e){
            Throwable throwable = Util.lastThrowable(e);
            Util.debugLogger.error("Insert Data Contact Phone Book Error | {} | {}", throwable.getMessage(), throwable.getCause());
            throw new ContactException(throwable.getMessage(), ContactHttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @Override
    public ContactDTO get(String param) throws ContactException {
        try {
            Contact contact = contactRepository.findAllByParam(param);
            return Util.INSTANCE_MAPPER.contactToContactDTO(contact);
        } catch (Exception e){
            Throwable throwable = Util.lastThrowable(e);
            Util.debugLogger.error("Get Data Contact Phone Book Error | {} | {}", throwable.getMessage(), throwable.getCause());
            throw new ContactException(throwable.getMessage(), ContactHttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Integer update(Contact contact) throws ContactException {
        int result = 0;
        try {
            result = contactRepository.updateDataPhoneBook(contact);
        } catch (Exception e){
            Throwable throwable = Util.lastThrowable(e);
            Util.debugLogger.error("Update Data Contact Phone Book Error | {} | {}", throwable.getMessage(), throwable.getCause());
            throw new ContactException(throwable.getMessage(), ContactHttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @Override
    public Integer delete(int contactId) throws ContactException {
        int result = 0;
        try {
            result = contactRepository.deleteContactById(contactId);
        } catch (Exception e){
            Throwable throwable = Util.lastThrowable(e);
            Util.debugLogger.error("Delete Data Contact Phone Book Error | {} | {}", throwable.getMessage(), throwable.getCause());
            throw new ContactException(throwable.getMessage(), ContactHttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
