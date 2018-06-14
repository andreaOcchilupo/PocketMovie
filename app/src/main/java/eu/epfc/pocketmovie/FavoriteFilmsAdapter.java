package eu.epfc.pocketmovie;

/**
 * Created by FHA576 on 14/06/2018.
 */

class FavoriteFilmsAdapter extends SWFilmsAdapter {

    public FavoriteFilmsAdapter(FragmentFavorite fragmentFavorite) {
        super(fragmentFavorite);
    }

    @Override
    public int getItemCount() {
        return (films == null) ? 0 : films.size();
    }


}
