package zikrirahman.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zikrirahman.crud.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, String>{
    
}
