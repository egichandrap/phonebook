package com.example.phonebook.infrastructure.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ContactHttpStatus {

    SUCCESS("200", "200:OK", "Success", "OK01", HttpStatus.OK),
    BAD_REQUEST("400", "400:NOK", "Invalid Parameters", "ERR01", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("500", "500:NOK", "Internal Server Error", "ERR02", HttpStatus.INTERNAL_SERVER_ERROR);

    private String statusCode;
    private String statusDesc;
    private String remarks;
    private String errorCode;
    private HttpStatus httpStatus;
}
