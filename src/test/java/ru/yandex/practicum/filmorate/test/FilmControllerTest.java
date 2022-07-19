package ru.yandex.practicum.filmorate.test;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    @Test
    // добавить фильм с пустым названием
    void addMovieWhenEmptyTitle() {
        FilmController filmController = new FilmController();
        Film film = new Film();
        film.setDescription("Каникулы в простоквашино.");
        film.setReleaseDate(LocalDate.of(1980, 2, 15));
        film.setDuration(15);
        assertThrows(RuntimeException.class, () -> filmController.addMovie(film));
    }

    @Test
    // когда описание фильма длинне 200 символов
    void addMovieWhenTooLongDescription() {
        FilmController filmController = new FilmController();
        Film film = new Film();
        film.setName("title");
        film.setDescription("sfsdfsdfsdfsdfsdfsdfsdfsdvfbghtrhthdfbcfgdrgdrggdggrggththjfghfthergdfdfgdgdfgdfgdfgdfgdfgbjghn" +
                "bgfjfgjfgjndfghfgjgkdgtjhfghsfgnfgmghmhgmghmdghmnhgmghmghmghmghmjgytdsergergdfbdfsbdfbnfgmngfmttgncfgnmf" +
                "dfgfgmghmghmcbmvbnmcbmchmgmgh,ghdgnfgnfgnxfgnvhmhmbnmcnhgfbxfbcvbcvngfssdfdfbdfbdfgjgjkukghngnxvbvbxcbfgb" +
                "mghmghmhgmghmbvmnxgbsdfsdfsdewecxcvxv;lk;kdsl;mdvmsldmlsmdlms;,;,smvdsdvsdnkvnkdsjnvknksjndvksdnvdvnlsndv" +
                "gfjdgnsfgaldfvmaldkfmbvadkfmbknadfnblamdflbkvmaldfmblakdmflkmblkmlakmdfkbmdkmflbmdlfmbfmblkmldfmblfbalbl");
        film.setReleaseDate(LocalDate.of(1980, 2,15));
        film.setDuration(15);
        assertThrows(RuntimeException.class, () -> filmController.addMovie(film));

    }

    @Test
    // добавить фильм когда неправильная дата тест
    void addMovieWhenDateIncorrectTest() {
        FilmController filmController = new FilmController();
        Film film = new Film();
        film.setName("title");
        film.setDescription("Каникулы в простокавашино");
        film.setReleaseDate(LocalDate.of(1234,11,22));
        film.setDuration(15);
        assertThrows(RuntimeException.class, () -> filmController.addMovie(film));
    }

    @Test
    // обновить фильм
    void updateFilmTest() {
        FilmController filmController = new FilmController();
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
        assertEquals("name",filmController.getId(1).getName());
    }
}