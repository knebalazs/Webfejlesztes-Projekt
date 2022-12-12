package hu.unideb.inf.webfejlesztesprojekt.controller;

import hu.unideb.inf.webfejlesztesprojekt.entity.Activity;
import hu.unideb.inf.webfejlesztesprojekt.repository.UserRepository;
import hu.unideb.inf.webfejlesztesprojekt.service.ActivityNotFoundException;
import hu.unideb.inf.webfejlesztesprojekt.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/summary")
    public String showSummary(Model model){
        Double totalCaloriesBurned = activityService.listAllByCurrentUser().stream()
                .map(Activity::getCaloriesBurned)
                .mapToDouble(Double::doubleValue)
                .sum();

        Integer totalWorkoutTime = activityService.listAllByCurrentUser().stream()
                .map(Activity::getWorkoutLength)
                .mapToInt(Integer::intValue)
                .sum();

        Integer totalDistanceDone = activityService.listAllByCurrentUser().stream()
                .map(Activity::getDistance)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();

        Integer totalRepetitions = activityService.listAllByCurrentUser().stream()
                .map(Activity::getRepetitions)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();

        model.addAttribute("totalCaloriesBurned", totalCaloriesBurned);
        model.addAttribute("totalWorkoutTime", totalWorkoutTime);
        model.addAttribute("totalDistanceDone", totalDistanceDone);
        model.addAttribute("totalRepetitions", totalRepetitions);
        return "/summary";
    }


    @GetMapping("/activities")
    public String showActivityList(Model model) {
        List<Activity> listActivities = activityService.listAllByCurrentUser();
        model.addAttribute("listActivities", listActivities);

        return "activities";
    }

    @GetMapping("/activities/new")
    public String showNewForm(Model model) {
        model.addAttribute("activity", new Activity());
        model.addAttribute("pageTitle", "Add New Activity");
        return "activity_form";
    }

    @PostMapping("activities/save")
    public String saveActivity(Activity activity, RedirectAttributes redirectAttributes) {
        Activity a = new Activity();
        a.setId(activity.getId());
        a.setWorkoutLength(activity.getWorkoutLength());
        a.setWorkoutType(activity.getWorkoutType());
        a.setDistance(activity.getDistance());
        a.setRepetitions(activity.getRepetitions());
        a.setUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        a.setCaloriesBurned(activityService.calculateCaloriesBurned(a.getWorkoutLength(), a.getWorkoutType(), userRepository.findByEmail(a.getUserEmail()).getBodyweight()));
        activityService.save(a);
        redirectAttributes.addFlashAttribute("message", "The activity has been added successfully.");
        return "redirect:/activities";
    }

    @GetMapping("/activities/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            System.out.println(id);
           Activity activity = activityService.get(id);
           model.addAttribute("activity", activity);
           model.addAttribute("pageTitle", "Edit Activity (ID: " + id + ")");
           return "activity_form";
        } catch (ActivityNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/activities";
        }
    }

    @GetMapping("activities/delete/{id}")
    public String deleteActivity(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
        activityService.delete(id);
        redirectAttributes.addFlashAttribute("message", "The activity ID " + id + "has been deleted");
        } catch (ActivityNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/activities";
    }

}
