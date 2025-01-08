package contactsManagement.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactRequest {
    private String id;
    private String firstName;
    private String surname;
    private String email;
    private String phoneNumber;
}
