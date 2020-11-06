package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final DBServiceUser usersService;

    public UserController(DBServiceUser usersService) {
        this.usersService = usersService;
    }

    @GetMapping({"/", "/user/list"})
    public String userListView(Model model) {
        List<User> users = usersService.getAllUsers();
//        List<AddressDataSet> listAddr = users.;
//        var users = usersService.getAllUsers().stream().findFirst().map(UserViewModel::new).stream().collect(Collectors.toList());
        model.addAttribute("users", users);
        return "users.html";
    }

    @GetMapping("/user/create")
    public String userCreateView(Model model) {
        model.addAttribute("user", new User());
        return "userCreate.html";
    }

    @PostMapping("/user/save")
    public RedirectView userSave(@ModelAttribute User user) {
        usersService.saveUser(user);
        return new RedirectView("/", true);
    }


}

class UserViewModel {
    long id = 0;
    String name = "";
    String address = "";
    String phone = "";

    public UserViewModel(User user) {
        id = user.getId();
        name = user.getName();
        address = user.getAddress().getStreet();

        user.getPhone().stream().findFirst().ifPresent(phoneDataSet -> phone = phoneDataSet.getNumber());
    }

}