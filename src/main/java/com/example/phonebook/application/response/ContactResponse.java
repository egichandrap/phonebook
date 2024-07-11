package com.example.phonebook.application.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponse {

    @JsonProperty("id")
    @SerializedName("id")
    private int Id;

    @JsonProperty("name")
    @SerializedName("name")
    private String name;

    @JsonProperty("phone_number")
    @SerializedName("phone_number")
    private String phone_number;
}
