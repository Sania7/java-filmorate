package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    // получить всех пользователей
    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    // добавить пользователя
    public void  addUser(User user) {
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        userStorage.addUser(user);
    }

    // обновить пользователя
    public void updateUser(User user) {
        checkId(user.getId());
        userStorage.updateUser(user);
    }

    // получить пользователя по id
    public User getUser(Integer id) {
        checkId(id);
        return userStorage.getUser(id);
    }

    // получить всех друзей по id
    public Set<User> getAllFriendsByUserId(Integer id) {
        checkId(id);
        return userStorage.getAllFriendsByUserId(id);
    }

    // получить создать нового друга
    public Set<User> getMutualFriends(Integer id, Integer otherId) {
        checkId(id);
        checkId(otherId);
        return userStorage.getMutualFriends(id, otherId);
    }

    // добавить друга
    public void addFriend(Integer id, Integer friendId) {
        checkId(id);
        checkId(friendId);
        userStorage.addFriend(id, friendId);
    }

    // удалить друга
    public void removeFriend(Integer id, Integer friendId) {
        checkId(id);
        checkId(friendId);
        userStorage.removeFriend(id, friendId);
    }

    public void checkId(Integer id) {
        if (!userStorage.checkUserById(id)) {
            log.debug("Нет пользователя с таким id {}", id);
            throw new ObjectNotFoundException("Нет пользователя с таким id");
        }
    }
}

