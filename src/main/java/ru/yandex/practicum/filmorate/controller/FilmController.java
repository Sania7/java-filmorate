package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    // хранилище фильмов
    private HashMap<Integer, Film> listFilms = new HashMap<>();
    private int newId = 0;

    // получение всех фильмов
    @GetMapping
    public Collection<Film> getListAllMovies() {
        return listFilms.values();
    }

    // добавление фильма
    @PostMapping
    public Film addMovie(@RequestBody Film film) {
        log.info("Запрос на добавление фильма.");
        if (film.getName().isBlank()) {
            log.debug("Отсутствует название фильма!");
            throw new ValidationException("Отсутствует название фильма!");
        }
        if (film.getDescription().length() > 200) {
            log.debug("Слишком длинное описание фильма!");
            throw new ValidationException("Слишком длинное описание фильма!");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.debug("Неверная дата выхода фильма!");
            throw new ValidationException("Неверная дата выхода фильма!");
        }
        if (film.getDuration() < 0) {
            log.debug("Продолжительность фильма неверная!");
            throw new ValidationException("Продолжительность фильма неверная!");
        }
        film.setId(getNewId());
        listFilms.put(film.getId(), film);
        return film;
    }

    // обновление фильма
    @PutMapping
    public Film updateMovie(@RequestBody Film film) {
        log.info("Введен запрос на изменение фильма.");
        if (!listFilms.containsKey(film.getId())) { //если список фильмов не содержит фильм с данным id
            log.debug("Несуществующий id!");
            throw new ValidationException("Нет фильма с таким id!");
        } else {
            listFilms.replace(film.getId(), film);
        }
        return film;
    }


    public Film getId(Integer id) {
        return listFilms.get(id);
    }

    private int getNewId() {
        return ++newId;
    }
}
