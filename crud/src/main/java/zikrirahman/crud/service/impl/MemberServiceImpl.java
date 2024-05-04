package zikrirahman.crud.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import zikrirahman.crud.entity.Member;
import zikrirahman.crud.repository.MemberRepository;
import zikrirahman.crud.service.MemberService;
import zikrirahman.crud.service.ValidatorService;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ValidatorService validatorService;

    @Override
    public void createMember(Member request) {
        validatorService.validator(request);
        memberRepository.save(request);
    }

    @Override
    public Member findByIdMember(String memberId) {
        Optional<Member> result = memberRepository.findById(memberId);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    @Override
    public Member updateMember(Member request, String memberId) {
        validatorService.validator(request);
        Member result = findByIdMember(memberId);
        if (result != null) {
            result.setName(request.getName());
            result.setTittle(request.getTittle());
            result.setDescription(request.getDescription());
            return memberRepository.save(result);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public void deleteMember(String memberId) {
        Member result = findByIdMember(memberId);
        if (result != null) {
            memberRepository.deleteById(memberId);
        }
    }
    
}
