package eu.epfc.pocketmovie.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jedepaepe on 11/06/2018.
 *
 * TODO : merge PMModel & SavedFilmManager
 */

public class PMModel {
    private static final PMModel ourInstance = new PMModel();
    private List<Film> films;


    static public PMModel getInstance() {
        return ourInstance;
    }

    private PMModel() {
    }

    public List<Film> getFilms() {
        if(films == null) films = new ArrayList<>();
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}
