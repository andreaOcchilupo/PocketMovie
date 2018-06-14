package eu.epfc.pocketmovie;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import eu.epfc.pocketmovie.model.Film;

/**
 * Created by FHA576 on 14/06/2018.
 */

class OnlineFilmsAdapter extends SWFilmsAdapter {

    public OnlineFilmsAdapter(FragmentOnline fragmentOnline) {
        super(fragmentOnline);
    }

    @Override
    public int getItemCount() {
        return (films == null) ? 0 : films.size() + 1;
    }


}
