package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Set;


public interface FilmStorage { // класс интерфейс хранилище фильмов


    Collection<Film> getFilms(); // получить все фильмы


    Film addFilm(Film film); // добавить фильм


    Film updateFilm(Film film); // обновить фильм


    Film getFilm(Integer id); // палучить фильм по id


    Set<Film> getPopularFilms(int count); // получить количество популярных фильмов


    void addLike(Integer id, Integer userId); // добавить лайк


    void deleteLike(Integer id, Integer userId); // удалить лайк


    boolean checkFilmById(Integer id); // проверить фильм по id
}
