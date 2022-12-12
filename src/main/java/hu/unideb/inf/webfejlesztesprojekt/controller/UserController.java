package hu.unideb.inf.webfejlesztesprojekt.controller;

import hu.unideb.inf.webfejlesztesprojekt.entity.User;
import hu.unideb.inf.webfejlesztesprojekt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Registration");
        return "/registration";
    }

    @PostMapping
    public String registerUserAccount(User user, Model model){
        model.addAttribute("user", new User());
        userService.save(user);
        return "redirect:/registration?success";
    }
}
