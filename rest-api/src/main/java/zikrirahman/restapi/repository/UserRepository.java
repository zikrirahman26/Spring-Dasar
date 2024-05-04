package zikrirahman.restapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zikrirahman.restapi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

    Optional<User> findFirstByToken(String token);
}
