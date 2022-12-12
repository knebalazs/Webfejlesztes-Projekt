package hu.unideb.inf.webfejlesztesprojekt.repository;

import hu.unideb.inf.webfejlesztesprojekt.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    Long countById(Integer id);
    User findByEmail(String email);
}
