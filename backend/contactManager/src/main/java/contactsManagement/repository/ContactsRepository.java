package contactsManagement.repository;

import contactsManagement.model.Contact;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ContactsRepository extends MongoRepository<Contact, String> {
    Optional<Contact> findById(ObjectId id);
}
