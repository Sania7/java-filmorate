package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    //  хранение данных - список пользователей
    private final HashMap<Integer, User> userList = new HashMap<>();
    private int newId = 0;

    // создать пользователя
    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        // получен запрос на добавление пользователя
        log.debug("Получен запрос на создание пользователя!");
        validation(user);
        user.setId(getNewId());
        userList.put(user.getId(), user);
        return user;
    }

    // обновление пользователя
    @PutMapping
    public User userUpdate(@Valid @RequestBody User user) {
        log.info("Получен запрос на обновление пользователя!");
        if (!userList.containsKey(user.getId())) {
            log.debug("Введен неверный id!");
            throw new ValidationException("Вы ввели не существующий id!");
        }
        userList.replace(user.getId(), user);
        return user;
    }


    // получить список всех пользователей
    @GetMapping
    public Collection<User> getListAllUsers() {
        return userList.values();
    }

    private void validation(User user) {
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Ошибка, введен некорректный формат email!");
        }
        if (user.getEmail().isEmpty() || user.getEmail().contains(" ")) {
            throw new ValidationException("Введите email адрес без пробелов!");
        }
        if (user.getLogin().isEmpty() && user.getLogin().contains(" ")) {
            throw new ValidationException("Ошибка! Введите логин без пробелов!");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Введена неверная дата рождения!");
        }
    }

    public User getUser(Integer id) {
        return userList.get(id);
    }

    private int getNewId() {
        return ++newId;
    }
}
