package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {


    private final HashMap<Integer, Film> movies = new HashMap<>(); // хранилище фильмов

    private int newId = 0; // счетчик


    @Override
    public Collection<Film> getFilms() { // получить все фильмы
        return movies.values();
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
    public Set<Film> getPopularFilms(int count) { // получить количество популярных фильмов
        List<Film> listFilms = new ArrayList<>(movies.values());
        TreeSet<Film> popularFilms = new TreeSet<>(Comparator.comparing(Film::getId).thenComparing(Film::getLikes).reversed());
        popularFilms.addAll(listFilms);
        while (popularFilms.size() > count) {
            popularFilms.pollLast();
        }
        return popularFilms;
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

    public boolean checkUserById(Integer userId) { // проверить пользователя по id
        return movies.containsKey(userId);
    }
    // метод генерации id фильма
    private int generateId() { // метод генерации id фильма
        return ++newId;
    }
}
