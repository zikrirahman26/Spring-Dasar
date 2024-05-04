package zikrirahman.crud.service;

import zikrirahman.crud.entity.Address;

public interface AddressService {
    
    Address createAddress (Address request, String memberId);
}
