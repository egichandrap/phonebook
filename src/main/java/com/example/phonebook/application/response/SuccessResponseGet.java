package com.example.phonebook.application.response;


import com.example.phonebook.infrastructure.util.ContactHttpStatus;
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
public class SuccessResponseGet extends BaseResponse{

    @SerializedName("response_time")
    private Long responseTime;

    @JsonProperty("data")
    @SerializedName("data")
    private ContactResponse contactResponse;

    public SuccessResponseGet(ContactHttpStatus contactHttpStatus, Long responseTime, ContactResponse contactResponse) {
        super(contactHttpStatus);
        this.responseTime = responseTime;
        this.contactResponse = contactResponse;
    }
}
