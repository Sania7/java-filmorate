package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {


    private final Map<Integer, User> users = new HashMap<>(); // хранилище пользоватeлей


    private int newId = 0; // счетчик



    @Override
    public Collection<User> getUsers() { // получить всех пользователей
        return new ArrayList<>(users.values());
    }


    @Override
    public User addUser(User user) { // добавить пользователя
        user.setId(generateId());
        users.put(user.getId(), user);
        return user;
    }


    @Override
    public User updateUser(User user) { // обновить пользователя
        users.replace(user.getId(), user);
        return user;
    }


    @Override
    public User getUser(Integer id) { // получить пользователя по id
        return users.get(id);
    }


    @Override
    public boolean checkUserById(Integer id) { // проверить пользователя по id
        return users.containsKey(id);
    }


    @Override
    public Set<User> getAllFriendsByUserId(Integer id) { // получить всех друзей пользователя по id
        return users.get(id).getFriends().stream()
                .map(users::get)
                .collect(Collectors.toSet());
    }


    @Override
    public Set<User> getMutualFriends(Integer id, Integer otherId) { // завести общих друзей

        Set<Integer> userFriendsIds = users.get(id).getFriends();
        Set<Integer> otherUserFriendsIds = users.get(otherId).getFriends();
        return otherUserFriendsIds.stream()
                .filter(userFriendsIds::contains)
                .map(users::get)
                .collect(Collectors.toSet());
    }


    @Override
    public void addFriend(Integer id, Integer friendId) { // добавить друга
        users.get(id).addFriend(friendId);
        users.get(friendId).addFriend(id);
    }


    @Override
    public void removeFriend(Integer id, Integer friendId) { // удалить друга
        users.get(id).deleteFriend(friendId);
        users.get(friendId).deleteFriend(id);
    }
    private int generateId() { // метод генерации id пользователя
        return ++newId;
    }
}
