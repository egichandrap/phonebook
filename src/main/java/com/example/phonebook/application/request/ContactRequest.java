package com.example.phonebook.application.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequest {

    @JsonProperty("name")
    @SerializedName("name")
    private String name;

    @JsonProperty("phone_number")
    @SerializedName("phone_number")
    private String phone_number;

    @JsonProperty("email")
    @SerializedName("email")
    private String email;
}
