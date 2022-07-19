package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Data
public class User {
    private int id;
    @NotNull
    private String email;
    @NotNull
    private String login;
    @NotNull
    private String name;
    @NotNull
    private LocalDate birthday;
}
