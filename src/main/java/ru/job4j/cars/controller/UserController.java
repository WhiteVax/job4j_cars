package ru.job4j.cars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static ru.job4j.cars.util.UserSession.getUser;

@Controller
public class UserController {
    private final UserService store;

    public UserController(UserService store) {
        this.store = store;
    }

    @GetMapping("/registration")
    public String registration(Model model, HttpSession session) {
        model.addAttribute("user", getUser(session));
        return "user/registration";
    }

    @PostMapping("/createUser")
    public String regOrFail(Model model, @ModelAttribute User user) {
        Optional<User> regUser = store.createUser(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message",
                    "Пользователь с таким логином уже зарегистрирован.");
            return "redirect:/fail";
        }
        return "redirect:/successfully";
    }

    @GetMapping("/fail")
    public String failReg(Model model, HttpSession session) {
        model.addAttribute("user", getUser(session));
        return "user/failReg";
    }

    @GetMapping("/successfully")
    public String successfullyReg(Model model, HttpSession session) {
        model.addAttribute("user", getUser(session));
        return "post/all";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail,
                            HttpSession session) {
        model.addAttribute("user", getUser(session));
        model.addAttribute("fail", fail != null);
        return "user/log";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> rsl = store.findByLoginAndPassword(
                user.getLogin(), user.getPassword()
        );
        if (rsl.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        var session = req.getSession();
        session.setAttribute("user", rsl.get());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}
