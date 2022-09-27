package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Component
public class InMemoryUserStorage implements UserStorage {


    private final HashMap<Integer, User> users = new HashMap<>(); // хранилище пользоватeлей


    private int newId = 0; // счетчик



    @Override
    public Collection<User> getUsers() { // получить всех пользователей
        return users.values();
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
        Set<User> friends = new HashSet<>();
        for (Integer ids : users.get(id).getFriends()) {
            friends.add(users.get(ids));
        }
        return friends;
    }


    @Override
    public Set<User> getMutualFriends(Integer id, Integer otherId) { // завести общих друзей
        Set<Integer> userFriendsIds = users.get(id).getFriends();
        Set<Integer> otherUserFriendsIds = users.get(otherId).getFriends();
        Set<Integer> mutualFriendsIds = new HashSet<>();
        for (Integer friendId : userFriendsIds) {
            for (Integer otherFriendId : otherUserFriendsIds) {
                if (friendId.equals(otherFriendId)) {
                    mutualFriendsIds.add(friendId);
                }
            }
        }
        Set<User> mutualFriends = new HashSet<>();
        for (Integer mfId : mutualFriendsIds) {
            mutualFriends.add(users.get(mfId));
        }
        return mutualFriends;
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
