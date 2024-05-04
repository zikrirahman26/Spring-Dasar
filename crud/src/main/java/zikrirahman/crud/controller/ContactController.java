package zikrirahman.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import zikrirahman.crud.dto.WebReponse;
import zikrirahman.crud.entity.Contact;
import zikrirahman.crud.service.ContactService;

@RestController
@RequestMapping("/api")
public class ContactController {
    
    @Autowired
    private ContactService contactService;

    @PostMapping("/members/{memberId}/contacts")
    public WebReponse<Contact> createContact(@PathVariable String memberId, @RequestBody Contact contact){
        Contact result = contactService.createContact(contact, memberId);
        return WebReponse.<Contact>builder().data(result).build();
    }
}
