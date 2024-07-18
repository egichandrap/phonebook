package com.example.phonebook.application.controller;


import com.example.phonebook.application.request.ContactRequest;
import com.example.phonebook.application.response.BaseResponse;
import com.example.phonebook.application.response.ContactResponse;
import com.example.phonebook.application.response.SuccessResponse;
import com.example.phonebook.application.response.SuccessResponseGet;
import com.example.phonebook.domain.data.ContactResultDTO;
import com.example.phonebook.domain.ports.api.ContactServicePort;
import com.example.phonebook.infrastructure.exception.ContactException;
import com.example.phonebook.infrastructure.util.ContactHttpStatus;
import com.example.phonebook.infrastructure.util.Util;
import com.google.gson.Gson;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactServicePort contactServicePort;
    private ContactResultDTO contactResultDTO;
    private ContactHttpStatus contactHttpStatus;
    private Object response;
    private long startTime;
    private long duration;


    public ContactController(ContactServicePort contactServicePort) {
        this.contactServicePort = contactServicePort;
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveContact (
            @RequestBody ContactRequest request
    ) {
        try {
            ThreadContext.put("msisdn", request.getPhone_number());
            Util.debugLogger.debug("Request = {}", new Gson().toJson(request));
            startTime = System.currentTimeMillis();
            contactResultDTO = contactServicePort.save(request);
            duration = System.currentTimeMillis() - startTime;
            contactHttpStatus = ContactHttpStatus.SUCCESS;
            response = new SuccessResponse(contactHttpStatus, duration);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ContactException exception) {
            duration = System.currentTimeMillis() - startTime;
            contactHttpStatus = exception.getContactHttpStatus();
            response = new BaseResponse(contactHttpStatus);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            duration = System.currentTimeMillis() - startTime;
            contactHttpStatus = ContactHttpStatus.INTERNAL_SERVER_ERROR;
            response = new BaseResponse(contactHttpStatus);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Util.debugLogger.debug("Result = {}|Response = {}|Save Contact Phone Book = {}", contactHttpStatus.getErrorCode(), new Gson().toJson(response), Util.isEmptyOrNull(contactResultDTO) ? null : new Gson().toJson(contactResultDTO.getContactDTO()));
            Util.tdrLogger.info("{}|{}|{}|{}|{}|{}",
                    contactHttpStatus.getStatusCode(),
                    contactHttpStatus.getRemarks(),
                    request.getPhone_number(),
                    Util.isNotEmptyOrNull(contactResultDTO) ? contactResultDTO.getContactDTO().getName() : "",
                    Util.isNotEmptyOrNull(contactResultDTO) ? contactResultDTO.getContactDTO().getEmail() : "",
                    duration
            );
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Object> getContact (
            @RequestParam(name = "param", required = true) String param // param ini bisa menggunakan nomor handphone atau nama
    ) {
        try {
            ThreadContext.put("data", param);
            Util.debugLogger.debug("Request = {}", param);
            startTime = System.currentTimeMillis();
            contactResultDTO = contactServicePort.get(param);
            ContactResponse contactResponse = new ContactResponse();
            contactResponse.setId(contactResultDTO.getContactDTO().getId());
            contactResponse.setName(contactResultDTO.getContactDTO().getName());
            contactResponse.setPhone_number(contactResultDTO.getContactDTO().getPhone_number());
            duration = System.currentTimeMillis() - startTime;
            contactHttpStatus = ContactHttpStatus.SUCCESS;
            response = new SuccessResponseGet(contactHttpStatus, duration, contactResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ContactException exception) {
            duration = System.currentTimeMillis() - startTime;
            contactHttpStatus = exception.getContactHttpStatus();
            response = new BaseResponse(contactHttpStatus);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            duration = System.currentTimeMillis() - startTime;
            contactHttpStatus = ContactHttpStatus.INTERNAL_SERVER_ERROR;
            response = new BaseResponse(contactHttpStatus);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Util.debugLogger.debug("Result = {}|Response = {}|Get Contact Phone Book = {}", contactHttpStatus.getErrorCode(), new Gson().toJson(response), Util.isEmptyOrNull(contactResultDTO) ? null : new Gson().toJson(contactResultDTO.getContactDTO()));
            Util.tdrLogger.info("{}|{}|{}|{}|{}|{}|{}",
                    contactHttpStatus.getStatusCode(),
                    contactHttpStatus.getRemarks(),
                    param,
                    Util.isNotEmptyOrNull(contactResultDTO) ? contactResultDTO.getContactDTO().getName() : "",
                    Util.isNotEmptyOrNull(contactResultDTO) ? contactResultDTO.getContactDTO().getPhone_number() : "",
                    Util.isNotEmptyOrNull(contactResultDTO) ? contactResultDTO.getContactDTO().getEmail() : "",
                    duration
            );
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateContact (
            @RequestParam(name = "id") int id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "phone_number", required = false) String phone,
            @RequestParam(name = "email", required = false) String email
    ) {
        try {
            Util.debugLogger.debug("Request = {}|{}|{}", name,phone,email);
            startTime = System.currentTimeMillis();
            contactServicePort.update(id, name, phone, email);
            duration = System.currentTimeMillis() - startTime;
            contactHttpStatus = ContactHttpStatus.SUCCESS;
            response = new SuccessResponse(contactHttpStatus, duration);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ContactException exception) {
            duration = System.currentTimeMillis() - startTime;
            contactHttpStatus = exception.getContactHttpStatus();
            response = new BaseResponse(contactHttpStatus);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            duration = System.currentTimeMillis() - startTime;
            contactHttpStatus = ContactHttpStatus.INTERNAL_SERVER_ERROR;
            response = new BaseResponse(contactHttpStatus);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Util.debugLogger.debug("Result = {}|Response = {}|Update Contact Phone Book = {}", contactHttpStatus.getErrorCode(), new Gson().toJson(response), Util.isEmptyOrNull(contactResultDTO) ? null : new Gson().toJson(contactResultDTO.getContactDTO()));
            Util.tdrLogger.info("{}|{}|{}|{}|{}|{}",
                    contactHttpStatus.getStatusCode(),
                    contactHttpStatus.getRemarks(),
                    name,
                    phone,
                    email,
                    duration
            );
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteContact (
            @RequestParam(name = "id") int id
    ) {
        try {
            startTime = System.currentTimeMillis();
            contactServicePort.delete(id);
            duration = System.currentTimeMillis() - startTime;
            contactHttpStatus = ContactHttpStatus.SUCCESS;
            response = new SuccessResponse(contactHttpStatus, duration);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ContactException exception) {
            duration = System.currentTimeMillis() - startTime;
            contactHttpStatus = exception.getContactHttpStatus();
            response = new BaseResponse(contactHttpStatus);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            duration = System.currentTimeMillis() - startTime;
            contactHttpStatus = ContactHttpStatus.INTERNAL_SERVER_ERROR;
            response = new BaseResponse(contactHttpStatus);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            Util.debugLogger.debug("Result = {}|Response = {}|Update Contact Phone Book = {}", contactHttpStatus.getErrorCode(), new Gson().toJson(response), Util.isEmptyOrNull(contactResultDTO) ? null : new Gson().toJson(contactResultDTO.getContactDTO()));
            Util.tdrLogger.info("{}|{}|{}",
                    contactHttpStatus.getStatusCode(),
                    contactHttpStatus.getRemarks(),
                    duration
            );
        }
    }
}
