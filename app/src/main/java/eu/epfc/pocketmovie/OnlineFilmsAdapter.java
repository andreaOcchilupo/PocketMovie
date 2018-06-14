package eu.epfc.pocketmovie;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by FHA576 on 14/06/2018.
 * Adapter for the Online version of the list
 * inherit of SWFilmAdapter, the basic adaptor to add a "NEXT" item at the end of the list
 */

class OnlineFilmsAdapter extends SWFilmsAdapter {

    public OnlineFilmsAdapter(FragmentOnline fragmentOnline) {
        super(fragmentOnline);
    }

    @Override
    public int getItemCount() {
        return (films == null) ? 0 : films.size() + 1;
    }

    @Override
    public void onBindViewHolder(FilmViewHolder holder, int position) {

        boolean isLast = (position == films.size() && position != 0);
        if(!isLast) {
            super.onBindViewHolder(holder, position);
        } else {
            ViewGroup viewGroup = (ViewGroup) holder.itemView;
            TextView title = viewGroup.findViewById(R.id.text_app_name);
            TextView rating = viewGroup.findViewById(R.id.text_title);
            ImageView poster = viewGroup.findViewById(R.id.image_poster);
            title.setVisibility(View.INVISIBLE);
            rating.setVisibility(View.INVISIBLE);
            poster.setVisibility(View.INVISIBLE);
            TextView nextPage = viewGroup.findViewById(R.id.text_next_page);
            nextPage.setVisibility(View.VISIBLE);
        }
    }


}
