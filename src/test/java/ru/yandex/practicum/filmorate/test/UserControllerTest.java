package ru.yandex.practicum.filmorate.test;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {

    @Test
    // когда имя пустое
    void addUserWhenBlankNameTest() {
        User user = new User();
        UserController userController = new UserController(new UserService(new InMemoryUserStorage()));
        user.setEmail("bob@yandex.ru");
        user.setLogin("login");
        user.setName("");
        user.setBirthday(LocalDate.of(1977,8,5));
        userController.createUser(user);
        assertEquals("login", userController.getUser(1).getName());
    }

    @Test
    // когда обновление user
    void whenUpdateUserTest() {
        UserController userController = new UserController(new UserService(new InMemoryUserStorage()));
        User user = new User();
        user.setEmail("bob@yandex.ru");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.of(1977,8,5));
        userController.createUser(user);
        User updateUser = new User();
        updateUser.setId(1);
        updateUser.setEmail("kord@yandex.ru");
        updateUser.setLogin("nextLogin");
        updateUser.setName("secondName");
        updateUser.setBirthday(LocalDate.of(2008,7,19));
        userController.userUpdate(updateUser);
        assertEquals("secondName", userController.getUser(1).getName());
    }
}