package eu.epfc.pocketmovie.model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 0107anocchilupo on 7/05/2018.
 */

public class Film implements Serializable {

    private String title;
    private String poster;//file
    private String backdrop;//file
    private double rate;
    private String releaseDate;
    private String summary;
    private String trailer;
    private ArrayList<Integer> genders;

    public ArrayList<Integer> getGenders() {
        return genders;
    }

    public String getGendersString() {
        String result = "";
        int i = 0;
        for (; i<genders.size()-1; i++) {
            result += (genders.get(i) + ",");
        }
        return result + genders.get(i);
    }

    public void setGenders(ArrayList<Integer> genders) {
        this.genders = genders;
    }

    public void setGenders(String genders) {
        try {
            String[] list = genders.split(genders);
            this.genders = new ArrayList<Integer>();
            for (String item: list) {
                this.genders.add(Integer.parseInt(item));
            }
        } catch (Exception ex) {
            Log.d(getClass().getName(), "setGenders: crashed \n" + ex.toString());
        }
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

    public Film(String title, String poster,String backdrop, double rate, String releaseDate, String summary, String trailer, ArrayList<Integer> genders) {
        this.title = title;
        this.poster = poster;
        this.backdrop = backdrop;
        this.rate = rate;
        this.releaseDate = releaseDate;
        this.summary = summary;
        this.trailer = trailer;
        this.genders = genders;
    }

    public Film(String title, String poster,String backdrop, double rate, String releaseDate, String summary, String trailer, String genders) {
        this.title = title;
        this.poster = poster;
        this.backdrop = backdrop;
        this.rate = rate;
        this.releaseDate = releaseDate;
        this.summary = summary;
        this.trailer = trailer;
        //this.setGenders( genders);
    }

    public Film(){

    }
}
