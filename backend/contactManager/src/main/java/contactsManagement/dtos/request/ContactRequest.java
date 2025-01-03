package contactsManagement.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactRequest {
    private String id;
    @NotBlank(message = "Name is required")
    private String firstName;
    private String surname;
    @Email(message = "please provide a valid email address")
    private String email;
    @NotBlank(message = "phone number is required")
    private String phoneNumber;
}
