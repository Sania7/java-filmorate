package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        checkUserById(user.getId());
        validation(user);
        inMemoryUserStorage.updateUser(user);
        return user;
    }

    // получить пользователя по id
    public User getUser(Integer id) {
        checkUserById(id);
        return inMemoryUserStorage.getUser(id);
    }

    // проверить пользователя по id
    public boolean checkUserById(Integer id) {
        return inMemoryUserStorage.checkUserById(id);
    }

    // получить всех друзей по id
    public Set<User> getAllFriendsByUserId(Integer id) {
        checkUserById(id);
        return inMemoryUserStorage.getAllFriendsByUserId(id);
    }

    // получить создать нового друга
    public Set<User> getMutualFriends(Integer id, Integer otherId) {
        checkUserById(id);
        checkUserById(otherId);
        return inMemoryUserStorage.getMutualFriends(id, otherId);
    }

    // добавить друга
    public void addFriend(Integer id, Integer friendId) {
        checkUserById(id);
        checkUserById(friendId);
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


//    // добавить лайк
//    public void addLike(Integer id, Integer userId) {
//        inMemoryUserStorage.addLike(id, userId);
//    }
//
//    // удалить лайк
//    public void removeLike(Integer id, Integer userId) {
//        inMemoryUserStorage.removeLike(id, userId);
//    }
}

