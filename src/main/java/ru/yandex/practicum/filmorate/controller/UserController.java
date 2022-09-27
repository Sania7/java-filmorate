package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @Autowired // конструктор
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping // 1.получить список всех пользователей
    public Collection<User> getListAllUsers() {
        return userService.getUsers();
    }



    @PostMapping // 2.создать пользователя
    public User createUser(@Valid @RequestBody User user) {
        // получен запрос на добавление пользователя
        log.info("Получен запрос на создание пользователя!");
        userService.addUser(user);
        return user;
    }


    @PutMapping // 3.обновление пользователя
    public User userUpdate(@Valid @RequestBody User user) {
        log.info("Получен запрос на обновление пользователя!");
        userService.updateUser(user);
        return user;
    }


    @GetMapping("/{id}") // 4.получить пользователя по id
    public User getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }


    @GetMapping("/{id}/friends/common/{otherId}") // 5.добавить новых друзей
    public Set<User> getMutualFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        return userService.getMutualFriends(id, otherId);
    }


    @GetMapping("/{id}/friends") // 6.получить друзей пользователя
    public Set<User> getAllFriendsByUserId(@PathVariable Integer id) {
        log.info("Получен запрос на отображения списка друзей");
        return userService.getAllFriendsByUserId(id);
    }


    @PutMapping("/{id}/friends/{friendId}") // 7.добавить друга
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        userService.addFriend(id, friendId);
    }


    @DeleteMapping("/{id}/friends/{friendId}") // 8.удалить друга
    public void removeFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        userService.removeFriend(id, friendId);
    }
}
