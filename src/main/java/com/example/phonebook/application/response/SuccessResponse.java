package com.example.phonebook.application.response;

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
public class SuccessResponse extends BaseResponse {

    @SerializedName("response_time")
    private Long responseTime;

    public SuccessResponse(ContactHttpStatus contactHttpStatus, Long responseTime) {
        super(contactHttpStatus);
        this.responseTime = responseTime;
    }
}
