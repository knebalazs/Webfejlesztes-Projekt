package hu.unideb.inf.webfejlesztesprojekt.service;

import hu.unideb.inf.webfejlesztesprojekt.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void save(User user);
    List<User> listAll();
    User get(Integer id) throws UserNotFoundException;
    void delete(Integer id) throws UserNotFoundException;
}
