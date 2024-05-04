package zikrirahman.restapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import zikrirahman.restapi.entity.User;
import zikrirahman.restapi.modelDTO.request.AddressCreateRequest;
import zikrirahman.restapi.modelDTO.request.AddressUpdateRequest;
import zikrirahman.restapi.modelDTO.response.AddressResponse;
import zikrirahman.restapi.modelDTO.response.WebResponse;
import zikrirahman.restapi.service.AddressService;

@RestController
public class AddressController {

        @Autowired
        private AddressService addressService;
        
        @PostMapping("/api/contacts/{contactId}/addresses")
        public WebResponse<AddressResponse> create(User user, @RequestBody AddressCreateRequest request,
                        @PathVariable("contactId") String contactId) {
                
                request.setContactId(contactId);
                AddressResponse addressResponse = addressService.create(user, request);
                
                return WebResponse.<AddressResponse>builder().data(addressResponse).build();
        }

        @GetMapping("/api/contacts/{contactId}/addresses/{addressId}")
        public WebResponse<AddressResponse> get(User user, @PathVariable("contactId") String contactId,
                        @PathVariable("addressId") String addressId) {
                
                AddressResponse addressResponse = addressService.get(user, contactId, addressId);
                
                return WebResponse.<AddressResponse>builder().data(addressResponse).build();
        }

        @PutMapping("/api/contacts/{contactId}/addresses/{addressId}")
        public WebResponse<AddressResponse> update(User user, @RequestBody AddressUpdateRequest request,
                        @PathVariable("contactId") String contactId, @PathVariable("addressId") String addressId) {
                
                request.setContactId(contactId);
                request.setAddressId(addressId);
                AddressResponse addressResponse = addressService.update(user, request);
                
                return WebResponse.<AddressResponse>builder().data(addressResponse).build();
        }

        @DeleteMapping("/api/contacts/{contactId}/addresses/{addressId}")
        public WebResponse<String> remove(User user, @PathVariable("contactId") String contactId,
                        @PathVariable("addressId") String addressId) {
                
                addressService.delete(user, contactId, addressId);
                
                return WebResponse.<String>builder().data("OK").build();
        }

        @GetMapping("/api/contacts/{contactId}/addresses")
        public WebResponse<List<AddressResponse>> list(User user, @PathVariable("contactId") String contactId) {
                
                List<AddressResponse> addressResponses = addressService.list(user, contactId);
                
                return WebResponse.<List<AddressResponse>>builder().data(addressResponses).build();
        }
}
