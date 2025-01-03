package contactsManagement.service;

import contactsManagement.dtos.request.ContactRequest;
import contactsManagement.dtos.response.ContactResponse;

import java.util.List;

public interface ContactService {
    ContactResponse addContact(ContactRequest contactRequest);
    ContactResponse findById(String id);
    List<ContactResponse> fetchContactsByName(String name);
    ContactResponse updateContact(String id, ContactRequest contactRequest);
    ContactResponse deleteById(String id);
    ContactResponse findAllContacts();
}
