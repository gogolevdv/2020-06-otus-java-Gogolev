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
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
        AddressDataSet ads = user.getAddress();

        model.addAttribute("user", user);
        model.addAttribute("address", ads);
        return "userCreate.html";
    }

    @PostMapping("/user/save")
    public RedirectView userSave(@ModelAttribute User user, @ModelAttribute AddressDataSet ads) {
        user.setAddress(ads);
        usersService.saveUser(user);
        return new RedirectView("/user/list", true);
    }

}

class UserViewModel {
    long id;
    String name;
    int age;
    String login = "";
    String password = "";
    AddressDataSet address;
    String phone = "";

    public UserViewModel(User user) {
        id = user.getId();
        name = user.getName();
        login = user.getLogin();
        password = user.getPassword();
        age = user.getAge();
        address = user.getAddress();
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

    public AddressDataSet getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}