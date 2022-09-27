package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    private final InMemoryUserStorage inMemoryUserStorage;

    //конструктор
    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    // получить всех пользователей
    public Collection<User> getUsers() {
        return inMemoryUserStorage.getUsers();
    }

    // добавить пользователя
    public User addUser(User user) {
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        validation(user);
        inMemoryUserStorage.addUser(user);
        return user;
    }

    // обновить пользователя
    public User updateUser(User user) {
        checkId(user.getId());
        validation(user);
        inMemoryUserStorage.updateUser(user);
        return user;
    }

    // получить пользователя по id
    public User getUser(Integer id) {
        checkId(id);
        return inMemoryUserStorage.getUser(id);
    }

    // проверить пользователя по id
    public boolean checkUserById(Integer id) {
        return inMemoryUserStorage.checkUserById(id);
    }

    // получить всех друзей по id
    public Set<User> getAllFriendsByUserId(Integer id) {
        checkId(id);
        return inMemoryUserStorage.getAllFriendsByUserId(id);
    }

    // получить создать нового друга
    public Set<User> getMutualFriends(Integer id, Integer otherId) {
        checkId(id);
        checkId(otherId);
        return inMemoryUserStorage.getMutualFriends(id, otherId);
    }

    // добавить друга
    public void addFriend(Integer id, Integer friendId) {
        checkId(id);
        checkId(friendId);
        inMemoryUserStorage.addFriend(id, friendId);
    }

    // удалить друга
    public void removeFriend(Integer id, Integer friendId) {
        checkUserById(id);
        checkUserById(friendId);
        inMemoryUserStorage.removeFriend(id, friendId);
    }

    public void validation(User user) {
        if (!user.getEmail().contains("@")) {
            log.debug("Неверный формат емейла {}", user.getEmail());
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
            log.debug("Неверная дата рождения {}", user.getBirthday());
            throw new ValidationException("Введена неверная дата рождения!");
        }
    }

    public void checkId(Integer id) {
        if (!inMemoryUserStorage.checkUserById(id)) {
            log.debug("Нет пользователя с таким id {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Нет пользователя с таким id");
        }
    }
}

