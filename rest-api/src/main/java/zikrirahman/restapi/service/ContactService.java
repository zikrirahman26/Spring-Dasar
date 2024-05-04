package zikrirahman.restapi.service;

import org.springframework.data.domain.Page;

import zikrirahman.restapi.entity.User;
import zikrirahman.restapi.modelDTO.request.ContactCreateRequest;
import zikrirahman.restapi.modelDTO.request.ContactSearchRequest;
import zikrirahman.restapi.modelDTO.request.ContactUpdateRequest;
import zikrirahman.restapi.modelDTO.response.ContactResponse;

public interface ContactService {
    
    ContactResponse create(User user, ContactCreateRequest request);

    ContactResponse update(User user, ContactUpdateRequest request);

    ContactResponse get(User user, String id);

    Page<ContactResponse> search(User user, ContactSearchRequest request);

    void delete(User user, String contactId);
}
