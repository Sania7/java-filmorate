package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Service
@Slf4j
public class FilmService {

    private static final LocalDate MIN_RELEASE_DATE =  LocalDate.of(1895,12,28);

    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final InMemoryUserStorage inMemoryUserStorage;


    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }






    public Collection<Film> getMovies() { // получить все фильмы
        return inMemoryFilmStorage.getFilms();
    }


    public Film addMovie(Film film) { // добавить фильм
        validation(film);
        inMemoryFilmStorage.addFilm(film);
        return film;
    }


    public Film updateMovie(Film film) { // обновить фильм
        checkFilmById(film.getId());
        validation(film);
        inMemoryFilmStorage.updateFilm(film);
        return film;
    }


    public Film getFilm(Integer id) { // получить фильм по id
        return inMemoryFilmStorage.getFilm(id);
    }


    public Set<Film> getPopularFilms(int count) { // получить количество популярных фильмов
        return inMemoryFilmStorage.getPopularFilms(count);
    }


    public void addLike(Integer id, Integer userId) { // добавить лайк
        inMemoryFilmStorage.addLike(id,userId);
    }


    public void removeLike(Integer id, Integer userId) { // удалить лайк
        inMemoryFilmStorage.deleteLike(id,userId);
    }


    public boolean checkFilmById(Integer id) { // проверить фильм по id
        return inMemoryFilmStorage.checkFilmById(id);
    }


    public void validation(Film film) { // валидация фильма перед созданием
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
}
