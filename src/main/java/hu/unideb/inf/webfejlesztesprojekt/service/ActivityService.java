package hu.unideb.inf.webfejlesztesprojekt.service;

import hu.unideb.inf.webfejlesztesprojekt.entity.Activity;
import hu.unideb.inf.webfejlesztesprojekt.entity.Workout;
import hu.unideb.inf.webfejlesztesprojekt.repository.ActivityRepository;
import hu.unideb.inf.webfejlesztesprojekt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public List<Activity> listAllByCurrentUser() {
        return (List<Activity>) activityRepository.findAllByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public void save(Activity activity) {
        activityRepository.save(activity);
    }

    public Activity get(Integer id) throws ActivityNotFoundException {
        Optional<Activity> result = activityRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ActivityNotFoundException("Could not find activity.");
    }

    public void delete(Integer id) throws ActivityNotFoundException {
        Long count = activityRepository.countById(id);
        if (count == null || count == 0){
            throw new ActivityNotFoundException("Could not find any activity with id.");
        }
        activityRepository.deleteById(id);
    }

    public Double calculateCaloriesBurned(Integer workoutLength, Workout workoutType, Integer bodyweight){
        return workoutLength*(workoutType.MetValue*3.5*bodyweight)/200;
    }
}
