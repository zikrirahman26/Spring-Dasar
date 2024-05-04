package zikrirahman.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zikrirahman.crud.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String>{
    
}
