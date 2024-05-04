package zikrirahman.crud.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import zikrirahman.crud.entity.Contact;
import zikrirahman.crud.entity.Member;
import zikrirahman.crud.repository.ContactRepository;
import zikrirahman.crud.service.ContactService;
import zikrirahman.crud.service.MemberService;
import zikrirahman.crud.service.ValidatorService;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepository contactRepository;
    
    @Autowired
    private MemberService memberService;

    @Autowired
    private ValidatorService validatorService;

    @Override
    public Contact createContact(Contact request, String memberId) {
        validatorService.validator(request);
        final Member member = memberService.findByIdMember(memberId);
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Contact save = contactRepository.save(request);

        List<Contact> contacts = new ArrayList<>(member.getContacts());
        contacts.add(save);
        member.setContacts(contacts);
        memberService.createMember(member);

        return request;
    }
}
