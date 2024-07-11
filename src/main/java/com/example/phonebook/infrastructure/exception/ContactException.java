package com.example.phonebook.infrastructure.exception;

import com.example.phonebook.infrastructure.util.ContactHttpStatus;

public class ContactException extends Exception {

    private final ContactHttpStatus contactHttpStatus;

    public ContactException(ContactHttpStatus contactHttpStatus) {
        super(contactHttpStatus.name());
        this.contactHttpStatus = contactHttpStatus;
    }

    public ContactException(String message, ContactHttpStatus contactHttpStatus) {
        super(message);
        this.contactHttpStatus = contactHttpStatus;
    }

    public ContactHttpStatus getContactHttpStatus(){
        return contactHttpStatus;
    }
}
