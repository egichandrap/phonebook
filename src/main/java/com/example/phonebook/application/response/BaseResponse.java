package com.example.phonebook.application.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.example.phonebook.infrastructure.util.ContactHttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {

    @JsonProperty("status_code")
    @SerializedName("status_code")
    private String statusCode;

    @JsonProperty("status_desc")
    @SerializedName("status_desc")
    private String statusDesc;

    @JsonProperty("remarks")
    @SerializedName("remarks")
    private String remarks;

    public BaseResponse(ContactHttpStatus contactHttpStatus) {
        this.statusCode = contactHttpStatus.getStatusCode();
        this.statusDesc = contactHttpStatus.getStatusDesc();
        this.remarks = contactHttpStatus.getRemarks();
    }
}
