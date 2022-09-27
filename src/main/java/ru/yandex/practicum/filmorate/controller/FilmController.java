package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;


@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;


    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    // 1.получение списка всех фильмов
    @GetMapping
    public Collection<Film> getListAllMovies() {
        return filmService.getMovies();
    }



    // 2.добавление фильма
    @PostMapping
    public Film addMovie(@Valid @RequestBody Film film) {
        log.info("Запрос на добавление фильма." + film);
        filmService.addMovie(film);
        return film;
    }

    // 3.обновление фильма
    @PutMapping
    public Film updateMovie(@Valid @RequestBody Film film) {
        log.info("Введен запрос на изменение фильма." + film);
        filmService.updateMovie(film);
        return film;
    }

    // 4.добавить лайк
    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        filmService.addLike(id,userId);
        //userService.addLike(id,userId);
    }

    // 5.удалить лайк
    @DeleteMapping("{id}/like/{userId}")
    public void removeLike(@PathVariable Integer id, @PathVariable Integer userId) {
        filmService.removeLike(id, userId);
        //.removeLike(id, userId);
    }

    // 6.получить фильм по id
    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Integer id) {
        log.info("Запрос фильма {}", id);
        return filmService.getFilm(id);
    }

    // 7.получить популярные фильмы count = 10
    @GetMapping("/popular")
    public Set<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getPopularFilms(count);
    }
}
