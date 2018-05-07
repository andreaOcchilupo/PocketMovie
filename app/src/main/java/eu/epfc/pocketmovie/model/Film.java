package eu.epfc.pocketmovie.model;

import java.util.ArrayList;

/**
 * Created by 0107anocchilupo on 7/05/2018.
 */

public class Film {

    private String title;
    private String poster;//file
    private String backdrop;//file
    private double rate;
    private String releaseDate;
    private String summary;
    private String trailer;
    private ArrayList<Integer> genres;

    public ArrayList<Integer> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Integer> genres) {
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public Film(String title, String poster,String backdrop, double rate, String releaseDate, String summary, String trailer, ArrayList<Integer> genres) {
        this.title = title;
        this.poster = poster;
        this.backdrop = backdrop;
        this.rate = rate;
        this.releaseDate = releaseDate;
        this.summary = summary;
        this.trailer = trailer;
        this.genres = genres;
    }

    public Film(){

    }
}
