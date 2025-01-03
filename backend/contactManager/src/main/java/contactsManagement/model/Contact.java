package contactsManagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Contacts")
public class Contact {
    @Id
    private String id;
    private String firstName;
    private String surname;
    private String fullName;
    private String email;
    private String phoneNumber;
}
