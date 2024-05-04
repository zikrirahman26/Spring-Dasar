package zikrirahman.restapi.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import zikrirahman.restapi.entity.Address;
import zikrirahman.restapi.entity.Contact;
import zikrirahman.restapi.entity.User;
import zikrirahman.restapi.modelDTO.request.AddressCreateRequest;
import zikrirahman.restapi.modelDTO.request.AddressUpdateRequest;
import zikrirahman.restapi.modelDTO.response.AddressResponse;
import zikrirahman.restapi.repository.AddressRepository;
import zikrirahman.restapi.repository.ContactRepository;
import zikrirahman.restapi.service.AddressService;
import zikrirahman.restapi.service.ValidationService;

@Service
public class AddressServiceImpl implements AddressService{

        @Autowired
        private AddressRepository addressRepository;
        
        @Autowired
        private ContactRepository contactRepository;

        @Autowired
        private ValidationService validationService;

        @Transactional
        @Override
        public AddressResponse create(User user, AddressCreateRequest request) {
                
                validationService.validate(request);

                Contact contact = contactRepository.findFirstByUserAndId(user, request.getContactId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.OK, "Kontak tidak ditemukan"));

                Address address = new Address();
                address.setId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
                address.setContact(contact);
                address.setStreet(request.getStreet());
                address.setCity(request.getCity());
                address.setProvince(request.getProvince());
                address.setCountry(request.getCountry());
                address.setPostalCode(request.getPostalCode());
                addressRepository.save(address);
                
                return toAddressResponse(address);
        }

        @Transactional
        @Override
        public AddressResponse update(User user, AddressUpdateRequest request) {
                
                validationService.validate(request);
                
                Contact contact = contactRepository.findFirstByUserAndId(user, request.getContactId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kontak tidak ditemukan"));

                Address address = addressRepository.findFirstByContactAndId(contact, request.getAddressId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alamat tidak ditemukan"));

                address.setStreet(request.getStreet());
                address.setCity(request.getCity());
                address.setProvince(request.getProvince());
                address.setCountry(request.getCountry());
                address.setPostalCode(request.getPostalCode());
                addressRepository.save(address);
                
                return toAddressResponse(address);
        }

        @Transactional(readOnly = true)
        @Override
        public AddressResponse get(User user, String contactId, String addressId) {

                Contact contact = contactRepository.findFirstByUserAndId(user, contactId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kontak tidak ditemukan"));

                Address address = addressRepository.findFirstByContactAndId(contact, addressId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alamat tidak ditemukan"));
                
                return toAddressResponse(address);
        }

        @Transactional
        @Override
        public void delete(User user, String contactId, String addressId) {
                
                Contact contact = contactRepository.findFirstByUserAndId(user, contactId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kontak tidak ditemukan"));

                Address address = addressRepository.findFirstByContactAndId(contact, addressId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alamat tidak ditemukan"));
                
                addressRepository.delete(address);
        }

        @Transactional(readOnly = true)
        @Override
        public List<AddressResponse> list(User user, String contactId) {
                
                Contact contact = contactRepository.findFirstByUserAndId(user, contactId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact is not found"));
                
                List<Address> addresses = addressRepository.findAllByContact(contact);
                
                return addresses.stream().map(this::toAddressResponse).toList();
        }

        
        //set address respon
        private AddressResponse toAddressResponse(Address address) {
                
                return AddressResponse.builder()
                        .id(address.getId())
                        .street(address.getStreet())
                        .city(address.getCity())
                        .province(address.getProvince())
                        .country(address.getCountry())
                        .postalCode(address.getPostalCode())
                        .build();
        }
}
