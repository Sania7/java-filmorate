package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Set;


@RequiredArgsConstructor
@Service
@Slf4j
public class FilmService {


    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Collection<Film> getMovies() { // получить все фильмы
        return filmStorage.getFilms();
    }


    public void addMovie(Film film) { // добавить фильм
        filmStorage.addFilm(film);
    }


    public void updateMovie(Film film) { // обновить фильм
        checkFilmById(film.getId());
        filmStorage.updateFilm(film);
    }


    public Film getFilm(Integer id) { // получить фильм по id
        checkFilmById(id);
        return filmStorage.getFilm(id);
    }


    public Set<Film> getPopularFilms(int count) { // получить количество популярных фильмов
        return filmStorage.getPopularFilms(count);
    }


    public void addLike(Integer id, Integer userId) { // добавить лайк
        filmStorage.addLike(id,userId);
    }


    public void removeLike(Integer id, Integer userId) { // удалить лайк
        checkFilmById(id);
        checkUserById(userId);
        filmStorage.deleteLike(id,userId);
    }


    public void checkFilmById(Integer id) { // проверить фильм по id
        if (!filmStorage.checkFilmById(id)) {
            log.debug("Неверный id фильма {} ", id);
            throw new ObjectNotFoundException("Нет фильма с таким id");
        }
        filmStorage.checkFilmById(id);
    }

    public void checkUserById(Integer id) {
        if (!userStorage.checkUserById(id)) {
            log.debug("Не существует пользователя с таким id {}",id);
            throw new ObjectNotFoundException("Не существует пользователя с таким id {}");
        }
    }
}
