package contactsManagement.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import contactsManagement.model.Contact;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ContactResponse {
    private String id;
    private String response;
    private String firstName;
    private String surName;
    private String fullName;
    private String email;
    private String phoneNumber;
    private List<Contact> contacts;
}
