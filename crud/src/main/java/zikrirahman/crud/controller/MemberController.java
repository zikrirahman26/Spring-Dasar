package zikrirahman.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import zikrirahman.crud.dto.WebReponse;
import zikrirahman.crud.entity.Member;
import zikrirahman.crud.service.MemberService;

@RestController
@RequestMapping("/api")
public class MemberController {
    
    @Autowired
    private MemberService memberService;

    @PostMapping("/members")
    public WebReponse<Member> createMember(@RequestBody Member member){
        memberService.createMember(member);
        return WebReponse.<Member>builder().data(member).build();
    }

    @GetMapping("/members/{memberId}")
    public WebReponse<Object> findByIdMember(@PathVariable String memberId){
        Member result = memberService.findByIdMember(memberId);
        return WebReponse.builder().data(result).build();
    }

    @GetMapping("/members")
    public WebReponse<List<Member>> findAllMember(){
        List<Member> result = memberService.findAllMember();
        return WebReponse.<List<Member>>builder().data(result).build();
    }

    @PatchMapping("/members/{memberId}")
    public WebReponse<Member> updateMember(@PathVariable String memberId, @RequestBody Member member){
        Member result = memberService.updateMember(member, memberId);
        return WebReponse.<Member>builder().data(result).build();
    }

    @DeleteMapping("/members/{memberId}")
    public WebReponse<Object> deleteMember(@PathVariable String memberId){
        memberService.deleteMember(memberId);
        return WebReponse.builder().data(memberId).build();
    }
}
