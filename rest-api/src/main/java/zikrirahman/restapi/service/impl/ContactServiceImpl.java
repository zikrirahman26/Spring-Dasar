package zikrirahman.restapi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.criteria.Predicate;
import zikrirahman.restapi.entity.Contact;
import zikrirahman.restapi.entity.User;
import zikrirahman.restapi.modelDTO.request.ContactCreateRequest;
import zikrirahman.restapi.modelDTO.request.ContactSearchRequest;
import zikrirahman.restapi.modelDTO.request.ContactUpdateRequest;
import zikrirahman.restapi.modelDTO.response.ContactResponse;
import zikrirahman.restapi.repository.ContactRepository;
import zikrirahman.restapi.service.ContactService;
import zikrirahman.restapi.service.ValidationService;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ValidationService validationService;

    //create kontak
    @Transactional
    @Override
    public ContactResponse create(User user, ContactCreateRequest request) {
        
        validationService.validate(request);

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contact.setUser(user);
        contactRepository.save(contact);
        
        return toContactResponse(contact);
    }

    @Transactional
    @Override
    public ContactResponse update(User user, ContactUpdateRequest request) {
        
        validationService.validate(request);

        Contact contact = contactRepository.findFirstByUserAndId(user, request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kontak tidak ditemukan"));

        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contactRepository.save(contact);

        return toContactResponse(contact);
    }

    @Transactional(readOnly = true)
    @Override
    public ContactResponse get(User user, String id) {
        
        Contact contact = contactRepository.findFirstByUserAndId(user, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kontak tidak ditemukan"));

        return toContactResponse(contact);
    }

    @Transactional
    @Override
    public void delete(User user, String contactId) {
        
        Contact contact = contactRepository.findFirstByUserAndId(user, contactId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kontak tidak ditemukan"));

        contactRepository.delete(contact);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ContactResponse> search(User user, ContactSearchRequest request) {
        
        Specification<Contact> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("user"), user));
            if (Objects.nonNull(request.getName())) {
                predicates.add(builder.or(
                        builder.like(root.get("firstName"), "%" + request.getName() + "%"),
                        builder.like(root.get("lastName"), "%" + request.getName() + "%")
                ));
            }
            if (Objects.nonNull(request.getEmail())) {
                predicates.add(builder.like(root.get("email"), "%" + request.getEmail() + "%"));
            }
            if (Objects.nonNull(request.getPhone())) {
                predicates.add(builder.like(root.get("phone"), "%" + request.getPhone() + "%"));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Contact> contacts = contactRepository.findAll(specification, pageable);
        List<ContactResponse> contactResponses = contacts.getContent().stream()
                .map(this::toContactResponse)
                .toList();

        return new PageImpl<>(contactResponses, pageable, contacts.getTotalElements());
    }


    //set contact respon
    private ContactResponse toContactResponse(Contact contact) {
        
        return ContactResponse.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .build();
    }
}
