package com.example.phonebook.infrastructure.mappers;

import com.example.phonebook.domain.data.ContactDTO;
import com.example.phonebook.infrastructure.entity.Contact;
import org.mapstruct.Mapper;

@Mapper
public interface ContactMapper {

    ContactDTO contactToContactDTO(Contact contact);

    Contact contactDTOtoContact(ContactDTO contactDTO);
}
