package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Set;

// класс интерфейс хранилище пользователей
public interface UserStorage {

    // получить всех пользователей
    Collection<User> getUsers();

    // добавить пользователя
    User addUser(User user);

    // обновиить пользователя
    User updateUser(User user);

    // получить пользователя по id
    User getUser(Integer id);

    // проверить пользователя по id
    boolean checkUserById(Integer id);

    // получить всех друзей пользователя по id
    Set<User> getAllFriendsByUserId (Integer id);

    // завести новых друзей
    Set<User> getMutualFriends(Integer id, Integer otherId);

    // добавить друга
    void addFriend(Integer id, Integer friendId);

    // удалить друга
    void removeFriend(Integer id, Integer friendId);

//    // добавить лайк
//    void addLike(Integer id, Integer userId);
//
//    // удалить лайк
//    void removeLike(Integer id, Integer userId);
}
