package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

import java.util.List;

@Controller
public class UserController {

    private final DBServiceUser usersService;

    public UserController(DBServiceUser usersService) {
        this.usersService = usersService;
    }

    @GetMapping({"/", "/user/list"})
    public String userListView(Model model) {
        List<User> users = usersService.getAllUsers();
        model.addAttribute("users", users);
        return "userList.html";
    }

//    @GetMapping("/user/create")
//    public String userCreateView(Model model) {
//        model.addAttribute("user", new User());
//        return "userCreate.html";
//    }
//
//    @PostMapping("/user/save")
//    public RedirectView userSave(@ModelAttribute User user) {
//        usersService.insertUser(user);
//        return new RedirectView("/", true);
//    }

}
