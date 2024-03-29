package ru.yandex.practicum.filmorate.model;

import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {

    private int id; // нумерация фильмов

    @NotBlank
    private String name; // название фильма

    @NotNull
    @Size(max = 200)
    private String description; // описание фильма

    @NotNull
    private LocalDate releaseDate; // дата выхода фильма


    @Positive
    private int duration; // длительность фильма

    private Set<Integer> likedUsersIds = new HashSet<>(); // хранилище лайков

    public void addLike(Integer id) { // добавить лайки
        this.likedUsersIds.add(id);
    }

    public void deleteLike(Integer id) { // удалить лайки
        this.likedUsersIds.remove(id);
    }

    public int getLikes() { // получить количество лайков
        return likedUsersIds.size();
    }
}
