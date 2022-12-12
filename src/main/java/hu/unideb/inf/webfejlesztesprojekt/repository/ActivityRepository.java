package hu.unideb.inf.webfejlesztesprojekt.repository;

import hu.unideb.inf.webfejlesztesprojekt.entity.Activity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActivityRepository extends CrudRepository<Activity, Integer> {
    Long countById(Integer id);
    List<Activity> findAllByUserEmail(String email);
}
