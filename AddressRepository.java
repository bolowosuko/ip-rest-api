package hello;

import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<IpAddress, String>{

}
