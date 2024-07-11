package com.example.phonebook.domain.data;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactResultDTO {

    Integer result;
    ContactDTO contactDTO;
}
