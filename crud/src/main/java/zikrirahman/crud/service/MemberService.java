package zikrirahman.crud.service;

import java.util.List;

import zikrirahman.crud.entity.Member;

public interface MemberService {
    
    void createMember(Member request);

    Member findByIdMember(String memberId);

    List<Member> findAllMember();

    Member updateMember(Member request, String memberId);

    void deleteMember(String memberId);
}
