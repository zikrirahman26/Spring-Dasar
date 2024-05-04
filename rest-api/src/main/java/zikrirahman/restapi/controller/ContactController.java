package zikrirahman.restapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import zikrirahman.restapi.entity.User;
import zikrirahman.restapi.modelDTO.request.ContactCreateRequest;
import zikrirahman.restapi.modelDTO.request.ContactSearchRequest;
import zikrirahman.restapi.modelDTO.request.ContactUpdateRequest;
import zikrirahman.restapi.modelDTO.response.ContactResponse;
import zikrirahman.restapi.modelDTO.response.PagingResponse;
import zikrirahman.restapi.modelDTO.response.WebResponse;
import zikrirahman.restapi.service.ContactService;

@RestController
public class ContactController {
    
    @Autowired
    private ContactService contactService;

    @PostMapping("/api/contacts")
    public WebResponse<ContactResponse> create(User user, @RequestBody ContactCreateRequest request) {
        
        ContactResponse contactResponse = contactService.create(user, request);
        
        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }

    @PutMapping("/api/contacts/{contactId}")
    public WebResponse<ContactResponse> update(User user, @RequestBody ContactUpdateRequest request,
            @PathVariable("contactId") String contactId) {

        request.setId(contactId);
        ContactResponse contactResponse = contactService.update(user, request);
        
        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }

    @GetMapping("/api/contacts/{contactId}")
    public WebResponse<ContactResponse> get(User user, @PathVariable("contactId") String contactId) {
        
        ContactResponse contactResponse = contactService.get(user, contactId);
        
        return WebResponse.<ContactResponse>builder().data(contactResponse).build();
    }

    @DeleteMapping("/api/contacts/{contactId}")
    public WebResponse<String> delete(User user, @PathVariable("contactId") String contactId) {
        
        contactService.delete(user, contactId);
        
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping("/api/contacts")
    public WebResponse<List<ContactResponse>> search(User user,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size) {
        
                ContactSearchRequest request = ContactSearchRequest.builder()
                .page(page)
                .size(size)
                .name(name)
                .email(email)
                .phone(phone)
                .build();

        Page<ContactResponse> contactResponses = contactService.search(user, request);
        return WebResponse.<List<ContactResponse>>builder()
                .data(contactResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(contactResponses.getNumber())
                        .totalPage(contactResponses.getTotalPages())
                        .size(contactResponses.getSize())
                        .build())
                .build();
    }
}
