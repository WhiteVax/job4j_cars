package ru.job4j.cars.util;

import javax.servlet.http.HttpSession;

import lombok.experimental.UtilityClass;
import ru.job4j.cars.model.User;

@UtilityClass
public class UserSession {
    private UserSession session;

    public static User getUser(HttpSession session) {
        var user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setLogin("Гость");
        }
        return user;
    }
}
