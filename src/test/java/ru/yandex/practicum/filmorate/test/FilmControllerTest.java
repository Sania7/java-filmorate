package ru.yandex.practicum.filmorate.test;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {

    @Test
    // добавить фильм с пустым названием
    void addMovieWhenEmptyTitle() {
        Film film = new Film();
        film.setDescription("Каникулы в простоквашино.");
        film.setReleaseDate(LocalDate.of(1980, 2, 15));
        film.setDuration(15);
    }

    @Test
    // когда описание фильма длинне 200 символов
    void addMovieWhenTooLongDescription() {
        Film film = new Film();
        film.setName("title");
        film.setDescription("sfsdfsdfsdfsdfsdfsdfsdfsdvfbghtrhthdfbcfgdrgdrggdggrggththjfghfthergdfdfgdgdfgdfgdfgdfgdfgbjghn" +
                "bgfjfgjfgjndfghfgjgkdgtjhfghsfgnfgmghmhgmghmdghmnhgmghmghmghmghmjgytdsergergdfbdfsbdfbnfgmngfmttgncfgnmf" +
                "dfgfgmghmghmcbmvbnmcbmchmgmgh,ghdgnfgnfgnxfgnvhmhmbnmcnhgfbxfbcvbcvngfssdfdfbdfbdfgjgjkukghngnxvbvbxcbfgb" +
                "mghmghmhgmghmbvmnxgbsdfsdfsdewecxcvxv;lk;kdsl;mdvmsldmlsmdlms;,;,smvdsdvsdnkvnkdsjnvknksjndvksdnvdvnlsndv" +
                "gfjdgnsfgaldfvmaldkfmbvadkfmbknadfnblamdflbkvmaldfmblakdmflkmblkmlakmdfkbmdkmflbmdlfmbfmblkmldfmblfbalbl");
        film.setReleaseDate(LocalDate.of(1980, 2,15));
        film.setDuration(15);
    }

    @Test
    void addMovieWhenWrongDurationTest() {
        FilmController filmController = new FilmController(new FilmService(new InMemoryFilmStorage(), new InMemoryUserStorage()));
        Film film = new Film();
        film.setName("name");
        film.setDescription("про маленького мальчика");
        film.setReleaseDate(LocalDate.of(2005, 9, 15));
        film.setDuration(-12);
        assertThrows(RuntimeException.class, () -> filmController.addMovie(film));
    }

    @Test
    // добавить фильм когда неправильная дата тест
    void addMovieWhenDateIncorrectTest() {
        FilmController filmController = new FilmController(new FilmService(new InMemoryFilmStorage(), new InMemoryUserStorage()));
        Film film = new Film();
        film.setName("title");
        film.setDescription("Каникулы в простокавашино");
        film.setReleaseDate(LocalDate.of(1234,11,22));
        film.setDuration(15);
        assertThrows(RuntimeException.class, () -> filmController.addMovie(film));
    }

    @Test
    // обновить фильм
    void updateMovieTest() {
        FilmController filmController = new FilmController(new FilmService(new InMemoryFilmStorage(), new InMemoryUserStorage()));
        Film film = new Film();
        film.setName("title");
        film.setDescription("Каникулы в простоквашино.");
        film.setReleaseDate(LocalDate.of(1986,2,15));
        film.setDuration(15);
        filmController.addMovie(film);
        Film updatedMovie = new Film();
        updatedMovie.setId(1);
        updatedMovie.setName("name");
        updatedMovie.setDescription("Кавказская пленница.");
        updatedMovie.setReleaseDate(LocalDate.of(1989,4,1));
        updatedMovie.setDuration(95);
        filmController.updateMovie(updatedMovie);
        assertEquals("name",filmController.getFilm(1).getName());
    }
}