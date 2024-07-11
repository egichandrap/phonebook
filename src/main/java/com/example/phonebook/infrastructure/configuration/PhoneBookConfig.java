package com.example.phonebook.infrastructure.configuration;

import com.example.phonebook.domain.ports.api.ContactServicePort;
import com.example.phonebook.domain.ports.spi.ContactPersistencePort;
import com.example.phonebook.domain.service.ContactServiceImpl;
import com.example.phonebook.infrastructure.adapters.ContactAdapter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PhoneBookConfig {

    @Bean
    public ContactPersistencePort contactPersistencePort(){
        return new ContactAdapter();
    }

    @Bean
    public ContactServicePort contactServicePort(){
        return new ContactServiceImpl(contactPersistencePort());
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


}
