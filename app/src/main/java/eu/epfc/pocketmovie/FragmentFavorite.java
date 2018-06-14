package eu.epfc.pocketmovie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import eu.epfc.pocketmovie.model.Film;
import eu.epfc.pocketmovie.model.HttpRequestService;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFavorite extends Fragment implements SWFilmsAdapter.ListItemClickListener {

    private RecyclerView filmRecyclerView;
    SWFilmsAdapter swFilmsAdapter;

    public FragmentFavorite() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        List<Film> films = SavedFilmManager.getInstance().getAllFims();

        filmRecyclerView = getView().findViewById(R.id.recyclerview_films);

        swFilmsAdapter = new FavoriteFilmsAdapter(this);
        filmRecyclerView.setAdapter(swFilmsAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        filmRecyclerView.setLayoutManager(layoutManager);

        swFilmsAdapter.setFilms(films);
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        List<Film> films = SavedFilmManager.getInstance().getAllFims();
        Film film = films.get(clickedItemIndex);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("filmObject", film);
        startActivity(intent);

        System.out.println("Item clicked " + clickedItemIndex);
    }

}
