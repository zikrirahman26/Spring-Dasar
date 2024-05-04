package zikrirahman.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import zikrirahman.crud.dto.WebReponse;
import zikrirahman.crud.entity.Address;
import zikrirahman.crud.service.AddressService;

@RestController
@RequestMapping("/api")
public class AddressController {
    
    @Autowired
    private AddressService addressService;

    @PostMapping("/members/{memberId}/addresses")
    public WebReponse<Address> createContact(@PathVariable String memberId, @RequestBody Address address){
        Address result = addressService.createAddress(address, memberId);
        return WebReponse.<Address>builder().data(result).build();
    }
}
