package controller;

import exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import model.Film;
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
        if (film.getName().isBlank()) {
            log.debug("Отсутствует название фильма!");
            throw new ValidationException("Отсутствует название фильма!");
        }
        if (film.getDescription().length() > 200) {
            log.debug("Слишком длинное описание!");
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
        listFilms.replace(film.getId(), film);
        return film;
    }


    public Film getId(Integer id) {
        return listFilms.get(id);
    }

    private int getNewId() {
        return ++newId;
    }
}
