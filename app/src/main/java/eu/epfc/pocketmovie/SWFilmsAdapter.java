package eu.epfc.pocketmovie;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import eu.epfc.pocketmovie.model.Film;

/**
 * Created by 0107anocchilupo on 14/05/2018.
 */

class SWFilmsAdapter extends RecyclerView.Adapter<SWFilmsAdapter.FilmViewHolder>{


    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    final private ListItemClickListener listItemClickListener;
    private List<Film> films;


    SWFilmsAdapter(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    public void setFilms(List<Film> films){
        this.films = films;

        this.notifyDataSetChanged();
    }

    @Override
    public FilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View filmView = layoutInflater.inflate(R.layout.item_film,parent,false);

        return new FilmViewHolder(filmView);
    }

    @Override
    public void onBindViewHolder(FilmViewHolder holder, int position) {
        Log.d(this.getClass().getName(), "BEFORE TRY CATCH ");

        boolean isLast = (position == films.size());

        ViewGroup viewGroup = (ViewGroup) holder.itemView;
        TextView title = viewGroup.findViewById(R.id.text_title);
        TextView rating = viewGroup.findViewById(R.id.text_rating);
        ImageView poster = viewGroup.findViewById(R.id.image_poster);
        if(!isLast) {
            Film film = films.get(position);
            title.setText(film.getTitle());
            rating.setText(viewGroup.getContext().getResources().getString(R.string.rating) + String.valueOf(film.getRate()));

            try {
                if (film.getPoster() != null && !film.getPoster().isEmpty()) {
                    String url = String.format(PMHelper.URL_POSTER, film.getPoster());
                    Picasso.get().load(url).into(poster);
                }
            } catch (Exception e) {
                Log.d(this.getClass().getName(), "PICASSO PICASSO PICASSO");
                Log.d(this.getClass().getName(), "PICASSO " + e.toString());

            }
            Log.d(this.getClass().getName(), "END TRY CATCH ");
        } else {
            title.setVisibility(View.INVISIBLE);
            rating.setVisibility(View.INVISIBLE);
            poster.setVisibility(View.INVISIBLE);
            TextView nextPage = viewGroup.findViewById(R.id.text_next_page);
            nextPage.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return (films == null) ? 0 : films.size() + 1;
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public FilmViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();
            SWFilmsAdapter.this.listItemClickListener.onListItemClick(clickPosition);
        }
    }
}
