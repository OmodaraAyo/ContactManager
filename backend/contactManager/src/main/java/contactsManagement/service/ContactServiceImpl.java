package contactsManagement.service;

import contactsManagement.dtos.request.ContactRequest;
import contactsManagement.dtos.response.ContactResponse;
import contactsManagement.model.Contact;
import contactsManagement.repository.ContactsRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactsRepository contactsRepository;

    @Autowired
    public ContactServiceImpl(ContactsRepository contactsRepository){
        this.contactsRepository = contactsRepository;
    }

    @Override
    public ContactResponse addContact(ContactRequest contactRequest) {
        validateInput(contactRequest.getFirstName(), contactRequest.getEmail(), contactRequest.getPhoneNumber());
        Contact savedContactResponse = saveContact(contactRequest);
        ContactResponse response = new ContactResponse();
        response.setId(savedContactResponse.getId());
        response.setResponse("Contact saved successfully");
        return response;
    }

    @Override
    public ContactResponse findById(String id) {
        ObjectId requestId = validateIdLength(id);
        Optional<Contact> returnedContact = contactsRepository.findById(requestId);
        if(returnedContact.isEmpty()) throw new RuntimeException("Contact not found");
        return setAndGetContactResponse(returnedContact);
    }

    @Override
    public List<ContactResponse> fetchContactsByName(String name) {
        String [] splitName = name.split(" ");
        if(name.isBlank() || splitName.length < 1 || splitName.length > 2) throw new RuntimeException("Contact not found");
        if(splitName.length == 1) return getContactsByName(splitName[0]);
        return getContactByFullName(splitName[0], splitName[1]);
    }

    @Override
    public ContactResponse updateContact(String id, ContactRequest contactRequest) {
        ObjectId requestId = validateIdLength(id);
        Optional<Contact> returnedContact = contactsRepository.findById(requestId);
        ContactResponse response = new ContactResponse();
        if(returnedContact.isEmpty()) response.setResponse("Contact does not exist");
        else getAndUpdateContact(contactRequest, returnedContact, response);
        return response;
    }

    @Override
    public ContactResponse deleteById(String id) {
        ObjectId requestId = validateIdLength(id);
        Optional<Contact> returnedContact = contactsRepository.findById(requestId);
        if(returnedContact.isEmpty()) throw new RuntimeException("Contact not found");
        return getDeletedContactResponse(returnedContact);
    }

    @Override
    public ContactResponse findAllContacts() {
        List<Contact> allContact = contactsRepository.findAll();
        ContactResponse response = new ContactResponse();
        if(allContact.isEmpty()) {
            response.setResponse("No Contact Available at the moment");
            return response;
        }
        response.setContacts(allContact);
        response.setResponse("All Contact Found");
        return response;
    }

    private ObjectId validateIdLength(String id) {
        if(id == null || id.isBlank()) throw new RuntimeException("Invalid id");
        if(id.length() != 24) throw new RuntimeException("Contact not found");
        return new ObjectId(id);
    }

    private void validateInput(String firstName, String email, String phoneNumber){
        if(firstName == null || firstName.isEmpty() || firstName.trim().isEmpty()) throw new RuntimeException("FirstName is required");
        validateEmail(email);
        if(phoneNumber == null || phoneNumber.isEmpty() || phoneNumber.trim().isEmpty()) throw new RuntimeException("PhoneNumber is required");
    }
    private void validateEmail(String email){
        String validEmail = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
        if(email == null || email.isEmpty() ||email.trim().isEmpty()) throw new RuntimeException("Email is required");
        if(!email.matches(validEmail)) throw new RuntimeException("Invalid email format");
    }

    private ContactResponse setAndGetContactResponse(Optional<Contact> contact) {
        ContactResponse response = new ContactResponse();
        String firstName = contact.get().getFirstName();
        String surname = contact.get().getSurname();
        response.setResponse("Contact found");
        response.setId(contact.get().getId());
        response.setFirstName(firstName);
        response.setSurName(surname);
        response.setFullName(firstName + " " + surname);
        response.setEmail(contact.get().getEmail());
        response.setPhoneNumber(contact.get().getPhoneNumber());
        return response;
    }

    private Contact saveContact(ContactRequest contactRequest) {
        String surname = validateSurName(contactRequest.getSurname());
        if(surname == null) return getContactWithoutSurnameField(contactRequest);
        else return fetchContact(contactRequest);
    }

    private String validateSurName(String surname){
        if(surname == null || surname.isEmpty()) return null;
        return surname.toUpperCase().replaceAll("\\s+","");
    }

    private Contact getContactWithoutSurnameField(ContactRequest contactRequest) {
        String firstName = contactRequest.getFirstName().toUpperCase().replaceAll("\\s+", "");
        Contact contact = new Contact();
        contact.setFirstName(firstName);
        contact.setFullName(firstName);
        contact.setEmail(contactRequest.getEmail().toUpperCase());
        contact.setPhoneNumber(contactRequest.getPhoneNumber().replaceAll("\\s+",""));
        return contactsRepository.save(contact);
    }
    private Contact fetchContact(ContactRequest contactRequest) {
        String firstName = contactRequest.getFirstName().toUpperCase().replaceAll("\\s+", "");
        String surname = contactRequest.getSurname().toUpperCase().replaceAll("\\s+", "");
        Contact contact = new Contact();
        contact.setFirstName(firstName);
        contact.setSurname(surname);
        contact.setFullName(firstName + " " + surname);
        contact.setEmail(contactRequest.getEmail().toUpperCase());
        contact.setPhoneNumber(contactRequest.getPhoneNumber().replaceAll("\\s+",""));
        return contactsRepository.save(contact);
    }

    public List<ContactResponse> findByFirstName(String firstName) {
        List<Contact> matchedContact = contactsRepository.findAll();
        return fetchAllContactsBy(matchedContact, firstName);
    }

    public List<ContactResponse> findBySurName(String secondName) {
        List<Contact> matchedContact = contactsRepository.findAll();
        return fetchAllContactsBy(matchedContact, secondName);
    }

    private List<ContactResponse> getContactsByName(String name) {
        List<ContactResponse> contactByFirstName = findByFirstName(name);
        List<ContactResponse> contactBySurname = findBySurName(name);
        if(contactByFirstName.isEmpty()) {
            return contactBySurname;
        }
        return contactByFirstName;
    }

    private List<ContactResponse> fetchAllContactsBy(List<Contact> matchContacts, String name) {
        List<ContactResponse> matchContactsResponse = new ArrayList<>();
        for(Contact contact : matchContacts) {
            ContactResponse contactResponse = new ContactResponse();
            if(contact.getFirstName().equals(name.toUpperCase()) || contact.getSurname().equals(name.toUpperCase())) {
                String firstName = contact.getFirstName().toUpperCase();
                String surname = contact.getSurname().toUpperCase();
                contactResponse.setResponse("Contact found");
                contactResponse.setId(contact.getId());
                contactResponse.setFirstName(firstName);
                contactResponse.setSurName(surname);
                contactResponse.setFullName(firstName + " " + surname);
                contactResponse.setEmail(contact.getEmail().toUpperCase());
                contactResponse.setPhoneNumber(contact.getPhoneNumber());
                matchContactsResponse.add(contactResponse);
            }
        }
        if(matchContactsResponse.isEmpty()){
            throw new RuntimeException("Contact not found");
        }
        return matchContactsResponse;
    }

    private void getAndUpdateContact(ContactRequest contactRequest, Optional<Contact> returnedContact, ContactResponse response) {
        validateInput(contactRequest.getFirstName(), contactRequest.getEmail(), contactRequest.getPhoneNumber());
        String newSurname = validateSurName(contactRequest.getSurname());
        if(newSurname == null) updateContactWithoutSurnameField(contactRequest, returnedContact, response);
        else fetchUpdatedContact(contactRequest, returnedContact, response);
    }

    private void updateContactWithoutSurnameField(ContactRequest contactRequest, Optional<Contact> returnedContact, ContactResponse response) {
        Contact updateContact = returnedContact.get();
        if(updateContact.getSurname().isEmpty())updateContactWithoutASurname(contactRequest,updateContact, response);
        else updateContactWithCurrentSurname(contactRequest, updateContact, response);
    }

    private void updateContactWithoutASurname(ContactRequest contactRequest, Contact updateContact, ContactResponse response) {
        String newFirstName = contactRequest.getFirstName().toUpperCase();
        updateContact.setFirstName(newFirstName);
        updateContact.setFullName(newFirstName);
        updateContact.setEmail(contactRequest.getEmail().toUpperCase());
        updateContact.setPhoneNumber(contactRequest.getPhoneNumber());
        contactsRepository.save(updateContact);
        response.setId(updateContact.getId());
        response.setResponse("Contact updated successfully");
    }

    private void updateContactWithCurrentSurname(ContactRequest contactRequest, Contact updateContact, ContactResponse response){
        String newFirstName = contactRequest.getFirstName().toUpperCase();
        String oldSurname = updateContact.getSurname();
        updateContact(contactRequest, updateContact, response, newFirstName, oldSurname);
    }

    private void updateContact(ContactRequest contactRequest, Contact updateContact, ContactResponse response, String firstName, String surname) {
        updateContact.setFirstName(firstName);
        updateContact.setSurname(surname);
        updateContact.setFullName(firstName + " " + surname);
        updateContact.setEmail(contactRequest.getEmail().toUpperCase());
        updateContact.setPhoneNumber(contactRequest.getPhoneNumber());
        contactsRepository.save(updateContact);
        response.setId(updateContact.getId());
        response.setResponse("Contact updated successfully");
    }

    private void fetchUpdatedContact(ContactRequest contactRequest, Optional<Contact> returnedContact, ContactResponse response) {
        Contact updateContact = returnedContact.get();
        String newFirstName = contactRequest.getFirstName().toUpperCase();
        String newSurname = contactRequest.getSurname().toUpperCase();
        updateContact(contactRequest, updateContact, response, newFirstName, newSurname);
    }
    private ContactResponse getDeletedContactResponse(Optional<Contact> contactToDelete) {
        contactsRepository.delete(contactToDelete.get());
        ContactResponse response = new ContactResponse();
        response.setId(contactToDelete.get().getId());
        response.setResponse("Contact deleted successfully");
        return response;
    }
    private List<ContactResponse> getContactByFullName(String firstName, String surname) {
        List<Contact> allContact = contactsRepository.findAll();
        if(allContact.isEmpty()) throw new RuntimeException("Contact not found");
        for(Contact contact : allContact) {
            if(contact.getFirstName().equalsIgnoreCase(firstName) && contact.getSurname().equalsIgnoreCase(surname)) {
                return fetchContactByFullName(contact);
            }
        }
        throw new RuntimeException("Contact not found");
    }

    private List<ContactResponse> fetchContactByFullName(Contact contact) {
        List<ContactResponse> contactResponses = new ArrayList<>();
        ContactResponse response = new ContactResponse();
        String firstName = contact.getFirstName();
        String surname = contact.getSurname();
        response.setResponse("Contact found");
        response.setId(contact.getId());
        response.setFirstName(firstName);
        response.setSurName(surname);
        response.setFullName(firstName + " " + surname);
        response.setEmail(contact.getEmail());
        response.setPhoneNumber(contact.getPhoneNumber());
        contactResponses.add(response);
        return contactResponses;
    }
}
