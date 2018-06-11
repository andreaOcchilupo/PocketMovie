package eu.epfc.pocketmovie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import eu.epfc.pocketmovie.model.Film;

/**
 * Created by jedepaepe on 28/05/2018.
 */

class SavedFilmManager {
    private FilmDataBaseHelper filmDataBaseHelper;
    private static final SavedFilmManager ourInstance = new SavedFilmManager();

    static SavedFilmManager getInstance() {
        return ourInstance;
    }

    private SavedFilmManager() {

    }

    public void initWithContext(Context context) {
        if(filmDataBaseHelper == null) {
            filmDataBaseHelper = new FilmDataBaseHelper(context);
        }
    }

    /**
     * Save the films in SQLite
     * @param films : List of film
     */
    public void saveFilms(List<Film> films) {
        filmDataBaseHelper.clearFilms();
        for (Film film: films) {
            filmDataBaseHelper.addFilm(film);
        }
    }


    /**
     * Return all films stored in DB
     * @return films stored in DB
     */
    public List<Film> getAllFims() {
        return filmDataBaseHelper.getAllFilms();
    }



    public void addFilm(Film film) {
        filmDataBaseHelper.addFilm(film);
    }

    public void removeFilm(Film film) {
        filmDataBaseHelper.removeFilm(film);
    }

    public boolean contains(Film film) {
        return getAllFims().contains(film);
    }


    /**
     * Helper to save and retrieve data from SQLite DB
     */
    class FilmDataBaseHelper extends SQLiteOpenHelper {

        /**
         * Name of the DB
         */
        private static final String DB_NAME = "FilmDB";

        /**
         * Version of the DB (usefull in case of migration of data model)
         */
        private static final int DB_VERSION = 1;

        /**
         * constructor
         * @param context : Android context
         */
        public FilmDataBaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        /**
         * Create the Database
         *      this methode is implicitly called when the first films are inserted
         * @param sqLiteDatabase
         */
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String statement = "CREATE TABLE FILM (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "TITLE TEXT," +
                    "POSTER TEXT," +
                    "BACKDROP TEXT," +
                    "RATE DOUBLE," +
                    "RELEASE_DATE TEXT," +
                    "SUMMARY TEXT," +
                    "TRAILER TEXT," +
                    "GENDERS TEXT)";
            sqLiteDatabase.execSQL(statement);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        }

        /**
         * Add a film in DB
         * @param film : the film
         */
        public void addFilm(Film film) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("TITLE", film.getTitle());
            contentValues.put("POSTER", film.getPoster());
            contentValues.put("BACKDROP", film.getBackdrop());
            contentValues.put("RATE", film.getRate());
            contentValues.put("RELEASE_DATE", film.getReleaseDate());
            contentValues.put("SUMMARY", film.getSummary());
            contentValues.put("TRAILER", film.getTrailer());
            contentValues.put("GENDERS", film.getGendersString());
            getWritableDatabase().insert("FILM", null, contentValues);
        }

        public void clearFilms() {
            String statement = "DELETE FROM FILM";
            getWritableDatabase().execSQL(statement);
        }

        public ArrayList<Film> getAllFilms() {
            ArrayList<Film> films = new ArrayList<>();
            Cursor cursor = null;
            try {
                cursor = filmDataBaseHelper.getReadableDatabase().query("FILM", new String[]{
                        "_id", "TITLE", "POSTER", "BACKDROP", "RATE", "RELEASE_DATE", "SUMMARY", "TRAILER", "GENDERS"
                }, null, null, null, null, null);
                // TODO check if for loop is necessary
                for(int i=0; i<cursor.getCount(); i++) {
                    if (cursor.moveToNext()) {
                        String title = cursor.getString(1);
                        String poster = cursor.getString(2);
                        String backdrop = cursor.getString(3);
                        Double rate = cursor.getDouble(4);
                        String releaseDate = cursor.getString(5);
                        String summary = cursor.getString(6);
                        String trailer = cursor.getString(7);
                        String genders = cursor.getString(8);
                        films.add(new Film(title, poster, backdrop, rate, releaseDate, summary, trailer, genders));
                    }
                }
            } catch (Exception ex) {
                Log.d(getClass().getName(), "getAllFilms: crashed \n" + ex.toString());
            } finally {
                if(cursor != null) cursor.close();
            }
            return films;
        }

        public void removeFilm(Film film) {
            String title = DatabaseUtils.sqlEscapeString(film.getTitle());
            String statement = "DELETE FROM FILM WHERE title = " + title;
            getWritableDatabase().execSQL(statement);
        }
    }
}
