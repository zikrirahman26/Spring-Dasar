package zikrirahman.crud.service;

import zikrirahman.crud.entity.Contact;

public interface ContactService {
    
    Contact createContact(Contact request, String memberId);
}
