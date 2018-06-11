package eu.epfc.pocketmovie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import eu.epfc.pocketmovie.model.PMModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOnline extends Fragment  implements SWFilmsAdapter.ListItemClickListener {

    private HttpRequestService httpRequestService = new HttpRequestService();
    private RecyclerView filmRecyclerView;
    SWFilmsAdapter swFilmsAdapter;

    public FragmentOnline() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_online, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        List<Film> films = PMModel.getInstance().getFilms();

        filmRecyclerView = getView().findViewById(R.id.recyclerview_films);

        swFilmsAdapter = new SWFilmsAdapter(this);
        filmRecyclerView.setAdapter(swFilmsAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        filmRecyclerView.setLayoutManager(layoutManager);

        FragmentOnline.HttpReceiver httpReceiver = new FragmentOnline.HttpReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("httpRequestComplete");
        intentFilter.addAction("httpRequestFailed");
        getActivity().registerReceiver(httpReceiver, intentFilter);

        String urlString = String.format(PMHelper.URL_POPULAR, 1, PMHelper.KEY);
        System.out.println("URL : " + urlString);
        HttpRequestService.startActionRequestHttp(getActivity().getApplicationContext(), urlString);

        swFilmsAdapter.setFilms(films);


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

        Film film = PMModel.getInstance().getFilms().get(clickedItemIndex);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("filmObject", film);
        startActivity(intent);

        System.out.println("Item clicked " + clickedItemIndex);
    }

    private class HttpReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals("httpRequestComplete")) {
                System.out.println("request complete");

                // get the JSON response from the intent
                String response = intent.getStringExtra("responseString");

                // parse the JSON into articles
                List<Film> films = parseTopStoriesResponse(response);

                // update singleton
                PMModel.getInstance().setFilms(films);

                // update the RecyclerView
                swFilmsAdapter.setFilms(films);
            }

            // else, if the request failed
            else if (intent.getAction().equals("httpRequestFailed")) {
                List<Film> films = SavedFilmManager.getInstance().getAllFims();
                PMModel.getInstance().setFilms(films);
                swFilmsAdapter.setFilms(films);
            }
        }
    }

}
