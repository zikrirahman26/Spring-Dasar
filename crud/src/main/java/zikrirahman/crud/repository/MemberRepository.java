package zikrirahman.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zikrirahman.crud.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String>{
    
}
