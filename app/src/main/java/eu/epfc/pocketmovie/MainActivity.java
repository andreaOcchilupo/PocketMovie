package eu.epfc.pocketmovie;

/*
* GnomePremier
* andreajean
* key : ea2dcee690e0af8bb04f37aa35b75075
* https://trello.com/b/HnDSqnM5/pocketmovies
* images http://image.tmdb.org/t/p/w185/
* */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eu.epfc.pocketmovie.model.Film;
import eu.epfc.pocketmovie.model.HttpRequestService;

public class MainActivity extends AppCompatActivity implements SWFilmsAdapter.ListItemClickListener{

    private HttpRequestService httpRequestService = new HttpRequestService();
    private RecyclerView filmRecyclerView;
    SWFilmsAdapter swFilmsAdapter;
    private List<Film> films;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        films = new ArrayList<>();
        filmRecyclerView = findViewById(R.id.recyclerview_films);

        swFilmsAdapter = new SWFilmsAdapter(this);
        filmRecyclerView.setAdapter(swFilmsAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        filmRecyclerView.setLayoutManager(layoutManager);

        HttpReceiver httpReceiver = new HttpReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("httpRequestComplete");
        intentFilter.addAction("httpRequestFailed");
        registerReceiver(httpReceiver, intentFilter);

        String urlString = String.format(PMHelper.URL_POPULAR, 1, PMHelper.KEY);
        System.out.println("URL : " + urlString);
        HttpRequestService.startActionRequestHttp(getApplicationContext(), urlString);

        swFilmsAdapter.setFilms(films);

        // Initialise le manager de FilmDB
        SavedFilmManager.getInstance().initWithContext(getApplicationContext());
    }

    private List<Film> parseTopStoriesResponse(String jsonString) {
        ArrayList<Film> films = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonFilms = jsonObject.getJSONArray("results");

            for (int i = 0; i < jsonFilms.length(); i++) {
                JSONObject jsonFilm = jsonFilms.getJSONObject(i);

                String title = jsonFilm.getString("title");
                String poster = jsonFilm.getString("poster_path");
                String backdrop = jsonFilm.getString("backdrop_path");
                double rate = jsonFilm.getDouble("vote_average");
                String release = jsonFilm.getString("release_date");
                String summary = jsonFilm.getString("overview");
                JSONArray jsonGenres = jsonFilm.getJSONArray("genre_ids");
                ArrayList<Integer> genres = new ArrayList<>();
                for(int j=0; j<jsonGenres.length(); j++){
                    genres.add(jsonGenres.optInt(j));
                }
                String trailer = ""; //TODO: https://api.themoviedb.org/3/movie/{your_movie_id}/videos ?api_key={your_api_key}

                Film newFilm = new Film(title, poster, backdrop, rate, release, summary, trailer, genres);

                films.add(newFilm);
            }

        } catch (JSONException e) {
            Log.e("TAG", "can't parse json string correctly");//TODO:tag
            e.printStackTrace();
        }

        return films;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        Film film = films.get(clickedItemIndex);

        System.out.println("Item clicked " + clickedItemIndex);
        //TODO : detail activity
    }

    private class HttpReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals("httpRequestComplete")) {
                System.out.println("request complete");

                // get the JSON response from the intent
                String response = intent.getStringExtra("responseString");

                // parse the JSON into articles
                films = parseTopStoriesResponse(response);

                // update the RecyclerView
                swFilmsAdapter.setFilms(films);

                // save those films in the database
                SavedFilmManager.getInstance().saveFilms(films);

                // List<Film> films = SavedFilmManager.getInstance().getAllFims(); // for test
            }

            // else, if the request failed
            else if (intent.getAction().equals("httpRequestFailed")) {
                List<Film> films = SavedFilmManager.getInstance().getAllFims();
                swFilmsAdapter.setFilms(films);
            }
        }
    }
}