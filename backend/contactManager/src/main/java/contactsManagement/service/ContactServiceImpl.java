package contactsManagement.service;

import contactsManagement.dtos.request.ContactRequest;
import contactsManagement.dtos.response.ContactResponse;
import contactsManagement.model.Contact;
import contactsManagement.repository.ContactsRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactsRepository contactsRepository;

    @Autowired
    public ContactServiceImpl(ContactsRepository contactsRepository){
        this.contactsRepository = contactsRepository;
    }

    @Override
    public ContactResponse addContact(ContactRequest contactRequest) {
        validateInput(contactRequest.getFirstName(), contactRequest.getSurname(), contactRequest.getEmail(), contactRequest.getPhoneNumber());
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
            response.setContacts(new ArrayList<>());
            return response;
        }
        response.setContacts(allContact);
        response.setResponse("All Contact Found");
        return response;
    }

    @Override
    public List<ContactResponse> findContactByAlphabet(String alphabet) {
        if(alphabet == null || alphabet.trim().isEmpty()) throw new RuntimeException("Contact not found");
        else return fetchContactsWithAlphabet(alphabet.trim().replaceAll("\\s+", ""));
    }

    private List<ContactResponse> fetchContactsWithAlphabet(String trimmedAlphabet){
        List<Contact> allContact = contactsRepository.findAll();
        List<ContactResponse> response = new ArrayList<>();
        List<String> addedContactIds = new ArrayList<>();
        sumCharsAndSetContactResponse(allContact, response, addedContactIds, trimmedAlphabet);
        return validateContactResponseListIsNotEmpty(response);
    }

    private void sumCharsAndSetContactResponse(List<Contact> allContact, List<ContactResponse> response, List<String> addedContactIds, String trimmedAlphabet){
        StringBuilder sumOfChar = new StringBuilder();
        trimmedAlphabet.chars().forEach(alphabets -> {
            char currentChar = (char) alphabets;
            setContactsResponseWithCurrentChar(allContact, addedContactIds, response, currentChar, sumOfChar);
        });
    }
    private void setContactsResponseWithCurrentChar(List<Contact>allContact, List<String> addedContactIds, List<ContactResponse> response, char currentChar, StringBuilder sumOfChar) {
        sumOfChar.append(currentChar);
        for(Contact contact : allContact) {
            if(contact.getFullName() != null && contact.getFullName().toLowerCase().contains(sumOfChar.toString().toLowerCase())) {
                validateContactAddedId(addedContactIds, response, contact);
            }
        }
    }

    private void validateContactAddedId(List<String> addedContactIds, List<ContactResponse> response, Contact contact){
        if(!addedContactIds.contains(contact.getId())) {
            creatListOfContactResponse(addedContactIds, response, contact);
        }
    }

    private void creatListOfContactResponse(List<String> addedContactIds, List<ContactResponse> response, Contact contact){
        ContactResponse contactResponse = new ContactResponse();
        contactResponse.setId(contact.getId());
        contactResponse.setFirstName(contact.getFirstName());
        contactResponse.setSurname(contact.getSurname());
        contactResponse.setFullName(contact.getFullName());
        contactResponse.setEmail(contact.getEmail());
        contactResponse.setPhoneNumber(contact.getPhoneNumber());
        response.add(contactResponse);
        addedContactIds.add(contact.getId());
    }

    private List<ContactResponse> validateContactResponseListIsNotEmpty(List<ContactResponse> contactResponse) {
        List<ContactResponse> contactResponseList = new ArrayList<>();
        if(contactResponse.isEmpty()){
            ContactResponse response = new ContactResponse();
            response.setResponse("No Contact found");
            response.setContacts(new ArrayList<>());
            contactResponseList.add(response);
        }
        else {
            contactResponseList.addAll(contactResponse);
        }
        return contactResponseList;
    }
    private ObjectId validateIdLength(String id) {
        if(id == null || id.isBlank()) throw new RuntimeException("Invalid id");
        if(id.length() != 24) throw new RuntimeException("Contact not found");
        return new ObjectId(id);
    }

    private void validateInput(String firstName, String surname, String email, String phoneNumber){
        if(firstName == null || firstName.isEmpty() || firstName.trim().isEmpty()) throw new RuntimeException("First name is required");
        checkIfNameAlreadyExists(firstName, surname);
        validateEmail(email);
        if(phoneNumber == null || phoneNumber.isEmpty() || phoneNumber.trim().isEmpty()) throw new RuntimeException("Phone number is required");
        if(!phoneNumber.matches("\\d+")) throw new RuntimeException("Phone number must contain only numeric digits: eg '1,2,3'");
    }
    private void checkIfNameAlreadyExists(String firstName, String surname){
        List<Contact> allContact = contactsRepository.findAll();
        allContact.stream()
                .filter(contact -> contact.getSurname() == null)
                .forEach(contact -> checkForNameWithoutSurname(contact, firstName));
        allContact.stream()
                .filter(contact -> contact.getSurname() != null)
                .forEach(contact -> checkForFullName(contact, firstName, surname));
    }
    public void checkForNameWithoutSurname(Contact contact, String firstName){
           if(contact.getFirstName().equals(firstName)) throw new RuntimeException("Contact with name already exist");
    }
    public void checkForFullName(Contact contact, String firstName, String surname){
            if(contact.getFirstName().equals(firstName) && contact.getSurname().equals(surname)) throw new RuntimeException("Contact with name already exist");
    }
    private void validateEmail(String email){
        String validEmail = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
        if(email == null || email.isEmpty() ||email.trim().isEmpty()) throw new RuntimeException("Email is required");
        if(!email.matches(validEmail)) throw new RuntimeException("Invalid email format");
    }

    private ContactResponse setAndGetContactResponse(Optional<Contact> contact) {
        ContactResponse response = new ContactResponse();
        String surname = validateSurName(contact.get().getSurname());
        if(surname == null) setContactResponseFieldWithoutSurname(contact, response);
        else setContactResponseField(contact, response);
        return response;
    }

    private void setContactResponseFieldWithoutSurname(Optional<Contact> contact, ContactResponse response) {
        String firstName = contact.get().getFirstName();
        response.setResponse("Contact found");
        response.setId(contact.get().getId());
        response.setFirstName(firstName);
        response.setFullName(firstName);
        response.setEmail(contact.get().getEmail());
        response.setPhoneNumber(contact.get().getPhoneNumber());
    }

    private void setContactResponseField(Optional<Contact> contact, ContactResponse response) {
        String firstName = contact.get().getFirstName();
        String surname = contact.get().getSurname();
        response.setResponse("Contact found");
        response.setId(contact.get().getId());
        response.setFirstName(firstName);
        response.setSurname(surname);
        response.setFullName(firstName + " " + surname);
        response.setEmail(contact.get().getEmail());
        response.setPhoneNumber(contact.get().getPhoneNumber());
    }

    private Contact saveContact(ContactRequest contactRequest) {
        String surname = validateSurName(contactRequest.getSurname());
        if(surname == null) return getContactWithoutSurnameField(contactRequest);
        else return fetchContact(contactRequest);
    }

    private String validateSurName(String surname){
        if(surname == null || surname.trim().isEmpty()) return null;
        return surname.replaceAll("\\s+","");
    }

    private Contact getContactWithoutSurnameField(ContactRequest contactRequest) {
        String firstName = contactRequest.getFirstName().trim().replaceAll("\\s+", " ");
        Contact contact = new Contact();
        contact.setFirstName(firstName);
        contact.setFullName(firstName);
        contact.setEmail(contactRequest.getEmail().toLowerCase());
        contact.setPhoneNumber(contactRequest.getPhoneNumber().trim().replaceAll("\\s+",""));
        return contactsRepository.save(contact);
    }
    private Contact fetchContact(ContactRequest contactRequest) {
        String firstName = contactRequest.getFirstName().trim().replaceAll("\\s+", " ");
        String surname = contactRequest.getSurname().trim().replaceAll("\\s+", " ");
        Contact contact = new Contact();
        contact.setFirstName(firstName);
        contact.setSurname(surname);
        contact.setFullName(firstName + " " + surname);
        contact.setEmail(contactRequest.getEmail().toLowerCase());
        contact.setPhoneNumber(contactRequest.getPhoneNumber().trim().replaceAll("\\s+",""));
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
            if(contact.getFirstName().equalsIgnoreCase(name) || contact.getSurname() == null || (contact.getSurname()).equalsIgnoreCase(name)) {
                setContactResponse(matchContactsResponse, contact, contactResponse);
            }
        }
        if(matchContactsResponse.isEmpty()){
            throw new RuntimeException("Contact not found");
        }
        return matchContactsResponse;
    }

    private void setContactResponse(List<ContactResponse> matchContactsResponse, Contact contact, ContactResponse contactResponse) {
        String firstName = contact.getFirstName();
        String surname = contact.getSurname();
//        if(surname == null) setResponseWithoutSurnameField(matchContactsResponse, contact, contactResponse, firstName);
        if(surname != null) setResponseWithAllField(matchContactsResponse, contact, contactResponse, firstName, surname);
    }

    private void setResponseWithoutSurnameField(List<ContactResponse> matchContactsResponse, Contact contact, ContactResponse contactResponse, String firstName) {
        contactResponse.setResponse("Contact found");
        contactResponse.setId(contact.getId());
        contactResponse.setFirstName(firstName);
        contactResponse.setFullName(firstName);
        contactResponse.setEmail(contact.getEmail());
        contactResponse.setPhoneNumber(contact.getPhoneNumber());
        matchContactsResponse.add(contactResponse);
    }

    private void setResponseWithAllField(List<ContactResponse> matchContactsResponse, Contact contact, ContactResponse contactResponse, String firstName, String surname) {
        contactResponse.setResponse("Contact found");
        contactResponse.setId(contact.getId());
        contactResponse.setFirstName(firstName);
        contactResponse.setSurname(surname);
        contactResponse.setFullName(firstName + " " + surname);
        contactResponse.setEmail(contact.getEmail());
        contactResponse.setPhoneNumber(contact.getPhoneNumber());
        matchContactsResponse.add(contactResponse);
    }

    private void getAndUpdateContact(ContactRequest contactRequest, Optional<Contact> returnedContact, ContactResponse response) {
        validateUpdateInput(contactRequest.getFirstName(), contactRequest.getEmail(), contactRequest.getPhoneNumber());
        String newSurname = validateSurName(contactRequest.getSurname());
        if(newSurname == null) updateContactWithoutSurnameField(contactRequest, returnedContact, response);
        if(returnedContact.get().getSurname() != null) updateContactWithNewSecondName(contactRequest, returnedContact, response);
        else updateAllContactFields(contactRequest, returnedContact, response);
    }

    private void validateUpdateInput(String firstName,  String email, String phoneNumber) {
        if(firstName == null || firstName.isEmpty() || firstName.trim().isEmpty()) throw new RuntimeException("First name is required");
        validateEmail(email);
        if(phoneNumber == null || phoneNumber.isEmpty() || phoneNumber.trim().isEmpty()) throw new RuntimeException("Phone number is required");
        if(!phoneNumber.matches("\\d+")) throw new RuntimeException("Phone number must contain only numeric digits: eg '1,2,3'");
    }

    private void updateContactWithNewSecondName(ContactRequest contactRequest, Optional<Contact> returnedContact, ContactResponse response){
        String newFirstName = contactRequest.getFirstName().trim().replaceAll("\\s+", " ");
        String newSurname = contactRequest.getSurname().trim().replaceAll("\\s+", " ");
        Contact updateContact = returnedContact.get();
        updateContact(contactRequest, updateContact, response, newFirstName,newSurname);
    }
    private void updateContactWithoutSurnameField(ContactRequest contactRequest, Optional<Contact> returnedContact, ContactResponse response) {
        Contact updateContact = returnedContact.get();
        if(updateContact.getSurname() == null || updateContact.getSurname().isEmpty())updateContactWithoutASurname(contactRequest,updateContact, response);
        else updateContactWithCurrentSurname(contactRequest, updateContact, response);
    }

    private void updateContactWithoutASurname(ContactRequest contactRequest, Contact updateContact, ContactResponse response) {
        String newFirstName = contactRequest.getFirstName().trim().replaceAll("\\s+", " ");
        updateContact.setFirstName(newFirstName);
        updateContact.setFullName(newFirstName);
        updateContact.setEmail(contactRequest.getEmail());
        updateContact.setPhoneNumber(contactRequest.getPhoneNumber());
        contactsRepository.save(updateContact);
        response.setId(updateContact.getId());
        response.setResponse("Contact updated successfully");
    }

    private void updateContactWithCurrentSurname(ContactRequest contactRequest, Contact updateContact, ContactResponse response){
        String newFirstName = contactRequest.getFirstName().trim().replaceAll("\\s+", " ");
        String oldSurname = updateContact.getSurname().trim().replaceAll("\\s+", " ");
        updateContact(contactRequest, updateContact, response, newFirstName, oldSurname);
    }

    private void updateAllContactFields(ContactRequest contactRequest, Optional<Contact> returnedContact, ContactResponse response) {
        Contact updateContact = returnedContact.get();
        String newFirstName = contactRequest.getFirstName();
        String newSurname = contactRequest.getSurname();
        updateContact(contactRequest, updateContact, response, newFirstName, newSurname);
    }

    private void updateContact(ContactRequest contactRequest, Contact updateContact, ContactResponse response, String firstName, String surname) {
        updateContact.setFirstName(firstName);
        updateContact.setSurname(surname);
        updateContact.setFullName(firstName + " " + surname);
        updateContact.setEmail(contactRequest.getEmail());
        updateContact.setPhoneNumber(contactRequest.getPhoneNumber());
        contactsRepository.save(updateContact);
        response.setId(updateContact.getId());
        response.setResponse("Contact updated successfully");
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
        setContactResponse(contactResponses, contact, response);
        return contactResponses;
    }
}
