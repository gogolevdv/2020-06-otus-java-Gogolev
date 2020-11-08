package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final DBServiceUser usersService;

    public UserController(DBServiceUser usersService) {
        this.usersService = usersService;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping({"/user/list"})
    public String userListView(Model model) {
        List<UserViewModel> users1 = new ArrayList<>();
        List<User> users = usersService.getAllUsers();

        for (User user : users) {
            usersService.getUser(user.getId()).ifPresentOrElse(x -> users1.add(new UserViewModel(x)), () -> logger.info("User not found"));
        }

        model.addAttribute("users", users1);
        return "users.html";
    }

    @GetMapping("/user/create")
    public String userCreateView(Model model) {
        User user = new User();
        UserViewModel newUser = new UserViewModel(new User());
        newUser.setAddress("qwasd");

        model.addAttribute("user", newUser);
        return "userCreate.html";
    }

    @PostMapping("/user/save")
    public RedirectView userSave(@ModelAttribute User user) {
        usersService.saveUser(user);
        return new RedirectView("/user/list", true);
    }


}


class UserViewModel {
    User user;
    long id;
    String name;
    int age;
    String login = "";
    String password = "";
    String address = "";
    String phone = "";

    public UserViewModel(User user) {
        this.user = user;
        id = user.getId();
        name = user.getName();
        login = user.getLogin();
        password = user.getPassword();
        age = user.getAge();
        address = user.getAddress().getStreet();
        user.getPhone().stream().findFirst().ifPresent(phoneDataSet -> phone = phoneDataSet.getNumber());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String street) {

       user.getAddress().setStreet(street);

    }

    public String getPhone() {
        return phone;
    }
}