package tacos.authorization.users;

import org.springframework.data.repository.CrudRepository;

public interface TacoUserRepository extends CrudRepository<TacoUser, Long> {

  TacoUser findByUsername(String username);
  
}
