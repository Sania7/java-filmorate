package ru.yandex.practicum.filmorate.model;

import lombok.Data;


import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    private int id; // id юзера

    @NotNull
    private String email; // email юзера

    @NotNull
    private String login; // login юзера

    @NotNull
    private String name; // имя юзера

    @NotNull
    private LocalDate birthday; // дата рождения юзера

    private Set<Integer> friends = new HashSet<>(); // список друзей



    public void addFriend(Integer id) { // добавить друзей
        friends.add(id);
    }


    public void deleteFriend(Integer id) { // удалить друзей
        friends.remove(id);
    }

}
