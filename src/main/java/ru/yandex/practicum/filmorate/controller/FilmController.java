package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;


@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    // хранилище фильмов
    private HashMap<Integer, Film> listFilms = new HashMap<>();

    private static final LocalDate MIN_RELEASE_DATE =  LocalDate.of(1895,12,28);
    private int newId = 0;

    // добавление фильма
    @PostMapping
    public Film addMovie(@RequestBody Film film) {
        log.info("Запрос на добавление фильма." + film);
        validation(film);
        film.setId(getNewId());
        listFilms.put(film.getId(), film);
        return film;
    }

    // обновление фильма
    @PutMapping
    public Film updateMovie(@RequestBody Film film) {
        log.info("Введен запрос на изменение фильма." + film);
        if (!listFilms.containsKey(film.getId())) { //если список фильмов не содержит фильм с данным id
            log.debug("Несуществующий id!");
            throw new ValidationException("Нет фильма с таким id!");
        }
        listFilms.replace(film.getId(), film);
        return film;
    }

    // получение всех фильмов
    @GetMapping
    public Collection<Film> getListAllMovies() {
        return listFilms.values();
    }

    private void validation(Film film) {
        if (film.getName().isBlank()) {
            throw new ValidationException("Отсутствует название фильма!");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Слишком длинное описание фильма!");
        }
        if (film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            throw new ValidationException("Неверная дата выхода фильма!");
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("Продолжительность фильма неверная!");
        }
    }


    public Film getId(Integer id) {
        return listFilms.get(id);
    }

    private int getNewId() {
        return ++newId;
    }
}
