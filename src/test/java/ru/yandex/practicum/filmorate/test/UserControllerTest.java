package ru.yandex.practicum.filmorate.test;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Test
    // когда неверный емайл
    void addUserWhenWrongEmailTest() {
        UserController userController = new UserController();
        User user = new User("email","login",LocalDate.of(1977,8,5));
        user.setEmail("mail.ru");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.of(1977,8,5));
        assertThrows(RuntimeException.class, () -> userController.createUser(user));
    }

    @Test
    // когда дата из будующего
    void addUserWhenWrongDateTest() {
        UserController userController = new UserController();
        User user = new User("@email","login",LocalDate.of(2277,8,5));
        user.setEmail("bob@yandex.ru");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.of(2024,11,7));
        assertThrows(RuntimeException.class,() -> userController.createUser(user));
    }

    @Test
    // когда имя пустое
    void addUserWhenBlankNameTest() {
        UserController userController = new UserController();
        User user = new User("@email","login",LocalDate.of(1977,8,5));
        user.setEmail("bob@yandex.ru");
        user.setLogin("login");
        user.setBirthday(LocalDate.of(1977,8,5));
        userController.createUser(user);
        assertEquals("login", userController.getUser(1).getName());
    }

    @Test
    // когда обновление user
    void whenUpdateUserTest() {
        UserController userController = new UserController();
        User user = new User("@email","login",LocalDate.of(1977,8,5));
        user.setEmail("bob@yandex.ru");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.of(1977,8,5));
        userController.createUser(user);
        User updateUser = new User("@email","login",LocalDate.of(1977,8,5));
        updateUser.setId(1);
        updateUser.setEmail("kord@yandex.ru");
        updateUser.setLogin("nextLogin");
        updateUser.setName("secondName");
        updateUser.setBirthday(LocalDate.of(2008,7,19));
        userController.userUpdate(updateUser);
        assertEquals("secondName", userController.getUser(1).getName());
    }
}