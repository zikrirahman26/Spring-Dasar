package zikrirahman.restapi.service;

import java.util.List;

import zikrirahman.restapi.entity.User;
import zikrirahman.restapi.modelDTO.request.AddressCreateRequest;
import zikrirahman.restapi.modelDTO.request.AddressUpdateRequest;
import zikrirahman.restapi.modelDTO.response.AddressResponse;

public interface AddressService {
    
    AddressResponse create(User user, AddressCreateRequest request);

    AddressResponse get(User user, String contactId, String addressId);

    AddressResponse update(User user, AddressUpdateRequest request);

    void delete(User user, String contactId, String addressId);

    List<AddressResponse> list(User user, String contactId);
}
