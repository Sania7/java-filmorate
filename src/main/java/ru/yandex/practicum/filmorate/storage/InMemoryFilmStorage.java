package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {


    private final Map<Integer, Film> movies = new HashMap<>(); // хранилище фильмов

    private int newId = 0; // счетчик


    @Override
    public Collection<Film> getFilms() { // получить все фильмы
        return new ArrayList<>(movies.values());
    }


    @Override
    public Film addFilm(Film film) { // добавить фильм
        film.setId(generateId());
        movies.put(film.getId(), film);
        return film;
    }


    @Override
    public Film updateFilm(Film film) { // обновить фильм
        movies.replace(film.getId(), film);
        return film;
    }


    @Override
    public Film getFilm(Integer id) { // получить фильм по id
        return movies.get(id);
    }


    @Override
    public List<Film> getPopularFilms(int count) { // получить количество популярных фильмов

        return movies.values().stream()
                .sorted(Comparator.comparing(Film::getLikes).reversed().thenComparing(Film::getId))
                .limit(count)
                .collect(Collectors.toList());
    }


    @Override
    public void addLike(Integer id, Integer userId) { // добавить лайк
        movies.get(id).addLike(userId);
    }

    @Override
    public void deleteLike(Integer id, Integer userId) { // удалить лайк
        movies.get(id).deleteLike(userId);
    }


    @Override
    public boolean checkFilmById(Integer id) { // проверить фильм по id
        return movies.containsKey(id);
    }


    // метод генерации id фильма
    private int generateId() { // метод генерации id фильма
        return ++newId;
    }
}
