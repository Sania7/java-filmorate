package ru.yandex.practicum.filmorate.model;

import lombok.Data;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    private int id; // id юзера

    @Email
    private String email; // email юзера

    @NotBlank
    private String login; // login юзера

    private String name; // имя юзера

    @PastOrPresent
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
