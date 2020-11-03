package ru.otus.controllers;

import org.springframework.web.bind.annotation.*;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

import java.util.Optional;

@RestController
public class UserRestController {

    private final DBServiceUser usersService;

    public UserRestController(DBServiceUser usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/api/user/{id}")
    public Optional<User> getUserById(@PathVariable(name = "id") long id) {
        return usersService.getUser(id);
    }

//    @GetMapping("/api/user")
//    public User getUserByName(@RequestParam(name = "name") String name) {
//        return usersService.findByName(name);
//    }

    @PostMapping("/api/user")
    public long saveUser(@RequestBody User user) { return usersService.saveUser(user); }

    @RequestMapping(method = RequestMethod.GET, value = "/api/user/random")
    public Optional<User> findRandom() {
        return usersService.getRandomUser();
    }

}
