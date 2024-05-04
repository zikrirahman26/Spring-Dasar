package zikrirahman.crud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import zikrirahman.crud.entity.Address;
import zikrirahman.crud.entity.Member;
import zikrirahman.crud.repository.AddressRepository;
import zikrirahman.crud.service.AddressService;
import zikrirahman.crud.service.MemberService;
import zikrirahman.crud.service.ValidatorService;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ValidatorService validatorService;

    @Override
    public Address createAddress(Address request, String memberId) {
        validatorService.validator(request);
        Member member = memberService.findByIdMember(memberId);
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Address save = addressRepository.save(request);
        List<Address> addresses = member.getAddresses();
        member.setAddresses(addresses);
        addresses.add(save);
        memberService.createMember(member);
        return request;
    }
    
}
