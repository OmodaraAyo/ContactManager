package contactsManagement.service;

import contactsManagement.dtos.request.ContactRequest;
import contactsManagement.dtos.response.ContactResponse;
import contactsManagement.repository.ContactsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ContactServiceImplTest {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactsRepository contactsRepository;


    @BeforeEach
    public void startAllWithThis() {
        contactsRepository.deleteAll();
        ContactRequest contactRequest = new ContactRequest();
        contactRequest.setFirstName("DAVID");
        contactRequest.setSurname("SuNNY");
        contactRequest.setEmail("johnmark@Gmail.com");
        contactRequest.setPhoneNumber("12345678911");
        contactService.addContact(contactRequest);
    }

    @Test
    public void testToFind_Saved_ContactById(){
        assertEquals(1, contactsRepository.count());
        ContactRequest contactRequest2 = new ContactRequest();
        contactRequest2.setFirstName("Benson");
        contactRequest2.setSurname("johnson");
        contactRequest2.setEmail("johnmark@Gmail.com");
        contactRequest2.setPhoneNumber("12345678911");
        ContactResponse savedContact = contactService.addContact(contactRequest2);
        assertEquals("Benson", contactService.findById(savedContact.getId()).getFirstName());
    }

    @Test
    public void testThatAnException_Is_Thrown_If_Id_Does_Not_Exist(){
        assertEquals(1, contactsRepository.count());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> contactService.findById("2"));
        assertEquals(exception.getMessage(), "Contact not found");
    }

    @Test
    public void test_to_findContact_By_FirstName(){
        assertEquals(1, contactsRepository.count());
        ContactRequest contactRequest2 = new ContactRequest();
        contactRequest2.setFirstName("JOHN");
        contactRequest2.setSurname("MARK");
        contactRequest2.setEmail("johndaniel@gmail.com");
        contactRequest2.setPhoneNumber("98765432123");
        contactService.addContact(contactRequest2);
        assertEquals(2, contactsRepository.count());

        ContactRequest contactRequest3 = new ContactRequest();
        contactRequest3.setFirstName("JOHN");
        contactRequest3.setSurname("DANIEL");
        contactRequest3.setEmail("johndaniel@gmail.com");
        contactRequest3.setPhoneNumber("765354241781");
        contactService.addContact(contactRequest3);
        assertEquals(3, contactsRepository.count());

        List<ContactRequest> expected = List.of(contactRequest2,contactRequest3);
        List<ContactResponse> actual = contactService.fetchContactsByName("JOHN");
        assertEquals(expected.size(), actual.size());
        for(ContactResponse contact : actual){
            assertEquals("JOHN", contact.getFirstName());
        }
    }

    @Test
    public void testThatAnExceptionIsThrown_If_firstName_Does_Not_Exist(){
        assertEquals(1, contactsRepository.count());
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> contactService.fetchContactsByName("Emma"));
        assertEquals(exception.getMessage(), "Contact not found");
    }

    @Test
    public void testTo_FindContactBy_SurName(){
        assertEquals(1, contactsRepository.count());
        ContactRequest contactRequest2 = new ContactRequest();
        contactRequest2.setFirstName("SON");
        contactRequest2.setSurname("MARK");
        contactRequest2.setEmail("johndaniel@gmail.com");
        contactRequest2.setPhoneNumber("98765432123");
        contactService.addContact(contactRequest2);
        assertEquals(2, contactsRepository.count());

        ContactRequest contactRequest3 = new ContactRequest();
        contactRequest3.setFirstName("JOHN");
        contactRequest3.setSurname("MARK");
        contactRequest3.setEmail("johndaniel@gmail.com");
        contactRequest3.setPhoneNumber("765354241781");
        contactService.addContact(contactRequest3);
        assertEquals(3, contactsRepository.count());

        List<ContactRequest> expected = List.of(contactRequest2,contactRequest3);
        List<ContactResponse> actual = contactService.fetchContactsByName("MARK");
        assertEquals(expected.size(), actual.size());
        for(ContactResponse contact : actual){
            assertEquals("MARK", contact.getSurname());
        }
    }

    @Test
    public void testThatAnException_Is_Thrown_If_Surname_Does_Not_Exist(){
        assertEquals(1, contactsRepository.count());
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> contactService.fetchContactsByName("JERRY"));
        assertEquals(exception.getMessage(), "Contact not found");
    }

    @Test
    public void testToDelete_A_Saved_ContactById(){
        assertEquals(1, contactsRepository.count());
        ContactRequest contactRequest2 = new ContactRequest();
        contactRequest2.setFirstName("JOHN");
        contactRequest2.setSurname("MARK");
        contactRequest2.setEmail("johndMark@gmail.com");
        contactRequest2.setPhoneNumber("12984384874");
        ContactResponse savedContact = contactService.addContact(contactRequest2);
        assertEquals(2, contactsRepository.count());
        contactService.deleteById(savedContact.getId());
        assertEquals(1, contactsRepository.count());
    }

    @Test
    public void testThatAnException_isThrown_if_id_to_Be_Deleted_does_not_Exist(){
        assertEquals(1, contactsRepository.count());
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> contactService.deleteById("2"));
        assertEquals(exception.getMessage(), "Contact not found");
    }

    @Test
    public void testToUpdate_A_Saved_ContactById(){
        assertEquals(1, contactsRepository.count());
        ContactRequest contactRequest2 = new ContactRequest();
        contactRequest2.setFirstName("Ayo");
        contactRequest2.setSurname("Maff");
        contactRequest2.setEmail("AyoMaff@gmail.com");
        contactRequest2.setPhoneNumber("09123456789");
        ContactResponse savedContact =  contactService.addContact(contactRequest2);
        assertEquals(2, contactsRepository.count());

        String updateId = savedContact.getId();
        ContactRequest contactRequest3 = new ContactRequest();
        contactRequest3.setFirstName("Emma");
        contactRequest3.setSurname("Wednesday");
        contactRequest3.setEmail("emmawednesday@gmail.com");
        contactRequest3.setPhoneNumber("09123456789");
        contactService.updateContact(updateId, contactRequest3);
        assertEquals(2, contactsRepository.count());
        assertEquals("Wednesday", contactsRepository.findById(updateId).orElseThrow(()-> new RuntimeException("Contact not found")).getSurname());
    }

    @Test
    public void testToFindAllContacts(){
        assertEquals(1, contactsRepository.count());

        ContactRequest contactRequest2 = new ContactRequest();
        contactRequest2.setFirstName("SON");
        contactRequest2.setSurname("MARK");
        contactRequest2.setEmail("johndaniel@gmail.com");
        contactRequest2.setPhoneNumber("98765432123");
        contactService.addContact(contactRequest2);
        assertEquals(2, contactsRepository.count());

        ContactRequest contactRequest3 = new ContactRequest();
        contactRequest3.setFirstName("JOHN");
        contactRequest3.setSurname("MARK");
        contactRequest3.setEmail("johndaniel@gmail.com");
        contactRequest3.setPhoneNumber("765354241781");
        contactService.addContact(contactRequest3);
        assertEquals(3, contactsRepository.count());

        ContactResponse getAllContacts = contactService.findAllContacts();
        assertEquals(3, getAllContacts.getContacts().size());
        assertEquals("DAVID", getAllContacts.getContacts().get(0).getFirstName());
        assertEquals("SON", getAllContacts.getContacts().get(1).getFirstName());
        assertEquals("JOHN", getAllContacts.getContacts().get(2).getFirstName());
    }

    @Test
    public void test_To_FindContactBy_FullName(){
        assertEquals(1, contactsRepository.count());
        ContactRequest contactRequest2 = new ContactRequest();
        contactRequest2.setFirstName("Ayodele");
        contactRequest2.setSurname("Benson");
        contactRequest2.setEmail("AyodeleBenson@gmail.com");
        contactRequest2.setPhoneNumber("234123456697");
        contactService.addContact(contactRequest2);
        assertEquals(2, contactsRepository.count());
        List<ContactResponse> foundContact = contactService.fetchContactsByName("Ayodele Benson");
        assertEquals("234123456697", foundContact.getFirst().getPhoneNumber());
    }

    @Test
    public void testThatAnExceptionIsThrownIf_FirstName_isEmpty(){
        assertEquals(1, contactsRepository.count());
        ContactRequest contactRequest2 = new ContactRequest();
        contactRequest2.setFirstName("");
        contactRequest2.setSurname("Benson");
        contactRequest2.setEmail("AyodeleBenson@gmail.com");
        contactRequest2.setPhoneNumber("234123456697");
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> contactService.addContact(contactRequest2));
        assertEquals(exception.getMessage(), "First name is required");
        assertEquals(1, contactsRepository.count());
    }

    @Test
    public void testThatContactCanBeSavedWithout_A_surname(){
        assertEquals(1, contactsRepository.count());
        ContactRequest contactRequest2 = new ContactRequest();
        contactRequest2.setFirstName("Benson");
        contactRequest2.setEmail("AyodeleBenson@gmail.com");
        contactRequest2.setPhoneNumber("234123456697");
        ContactResponse savedContact = contactService.addContact(contactRequest2);
        assertEquals(2, contactsRepository.count());
        assertEquals("Benson", contactService.findById(savedContact.getId()).getFirstName());
        assertEquals(2, contactsRepository.count());
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> contactService.fetchContactsByName(""));
        assertEquals(exception.getMessage(), "Contact not found");
    }

    @Test
    public void testThatAn_ExceptionIsThrown_If_email_Is_Invalid(){
        assertEquals(1, contactsRepository.count());
        ContactRequest contactRequest2 = new ContactRequest();
        contactRequest2.setFirstName("Ayodele");
        contactRequest2.setSurname("Benson");
        contactRequest2.setEmail("Ayodele  @gmail.com");
        contactRequest2.setPhoneNumber("234123456697");
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> contactService.addContact(contactRequest2));
        assertEquals(exception.getMessage(), "Invalid email format");
        assertEquals(1, contactsRepository.count());
    }
    @Test
    public void testThatAn_ExceptionIsThrown_If_phoneNumber_Is_Empty(){
        assertEquals(1, contactsRepository.count());
        ContactRequest contactRequest2 = new ContactRequest();
        contactRequest2.setFirstName("Ayodele");
        contactRequest2.setSurname("Benson");
        contactRequest2.setEmail("Ayodele@gmail.com");
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> contactService.addContact(contactRequest2));
        assertEquals(exception.getMessage(), "Phone number is required");
        assertEquals(1, contactsRepository.count());
    }

    @Test
    public void testThatIfContactTo_Save_Already_Exist_An_Exception_Is_Thrown(){
        assertEquals(1, contactsRepository.count());
        ContactRequest contactRequest2 = new ContactRequest();
        contactRequest2.setFirstName("DAVID");
        contactRequest2.setSurname("SuNNY");
        contactRequest2.setEmail("johnmark@Gmail.com");
        contactRequest2.setPhoneNumber("12345678911");
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> contactService.addContact(contactRequest2));
        assertEquals(exception.getMessage(), "Contact with name already exist");
    }

    @Test
    public void testToFindContactByAlphabet(){
        assertEquals(1, contactsRepository.count());
        ContactRequest contactRequest2 = new ContactRequest();
        contactRequest2.setFirstName("Ayodele");
        contactRequest2.setSurname("Benson");
        contactRequest2.setEmail("AyodeleBenson@gmail.com");
        contactRequest2.setPhoneNumber("234123456697");
        contactService.addContact(contactRequest2);
        assertEquals(2, contactsRepository.count());

        ContactRequest contactRequest3 = new ContactRequest();
        contactRequest3.setFirstName("John");
        contactRequest3.setSurname("Savage");
        contactRequest3.setEmail("savage21@gmail.com");
        contactRequest3.setPhoneNumber("090235477929");
        contactService.addContact(contactRequest3);
        assertEquals(3, contactsRepository.count());

        List<ContactResponse> matchedContact = contactService.findContactByAlphabet("av");
        System.out.println(matchedContact);
        for(ContactResponse contactResponse: matchedContact){
            assertTrue(contactResponse.getFullName().toLowerCase().contains("a"));
        }
    }
}