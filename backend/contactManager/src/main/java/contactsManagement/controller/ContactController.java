package contactsManagement.controller;

import contactsManagement.dtos.request.ContactRequest;
import contactsManagement.dtos.response.ContactResponse;
import contactsManagement.service.ContactServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactServiceImpl contactService;

    @Autowired
    public ContactController(ContactServiceImpl contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/addContact")
    public ContactResponse createContact(@RequestBody @Valid ContactRequest contactRequest) {
         return contactService.addContact(contactRequest);
    }

    @GetMapping("/getContactById/{id}")
    public ContactResponse getContactsById(@PathVariable("id") String id) {
        return contactService.findById(id);
    }

    @GetMapping("/getContactByFirstName")
    public List<ContactResponse> getContactsByFirstName(@RequestParam(defaultValue = "Unknown") String firstName) {
        return contactService.fetchContactsByName(firstName);
    }
    @GetMapping("/getContactBySurname")
    public List<ContactResponse> getContactsBySurname(@RequestParam(defaultValue = "Unknown") String surname) {
        return contactService.fetchContactsByName(surname);
    }
    @GetMapping("/getContactByFullName")
    public List<ContactResponse> getContactByFullName(@RequestParam(defaultValue = "Unknown") String fullName) {
        return contactService.fetchContactsByName(fullName);
    }
    @GetMapping("/getAllContacts")
    public ContactResponse getAllContacts() {
        return contactService.findAllContacts();
    }

    @PatchMapping("/updateContact/{id}")
    public ContactResponse updateContact(@PathVariable("id") String id, @RequestBody @Valid ContactRequest contactRequest) {
        return contactService.updateContact(id, contactRequest);
    }
    @DeleteMapping("/deleteById/{id}")
    public ContactResponse deleteContactById(@PathVariable("id") String id) {
        return contactService.deleteById(id);
    }

}
