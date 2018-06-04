package eu.epfc.pocketmovie;

/**
 * Created by 0107anocchilupo on 7/05/2018.
 */

import android.support.constraint.solver.widgets.Helper;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import eu.epfc.pocketmovie.model.Film;

/**
 * contains constants url, key, etc
 */
public class PMHelper {
    public static final String KEY = "ea2dcee690e0af8bb04f37aa35b75075";
    public static final String URL_POPULAR = "https://api.themoviedb.org/3/movie/popular?page=%d&api_key=%s";
    public static final String URL_POSTER = "http://image.tmdb.org/t/p/w185/%s";

    public static void setImageView(ImageView imageView, String urlString) {
        try {
            if (urlString != null && !urlString.isEmpty()) {
                String url = String.format(PMHelper.URL_POSTER, urlString);
                Picasso.get().load(url).into(imageView);
            }
        } catch (Exception e) {
            Log.d(Helper.class.getName(), "PICASSO PICASSO PICASSO");
            Log.d(Helper.class.getName(), "PICASSO " + e.toString());
        }
    }

}
